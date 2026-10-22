package com.srini.demo.service;

import com.srini.demo.dto.OrderCreateRequest;
import com.srini.demo.dto.OrderItemRequest;
import com.srini.demo.dto.OrderItemResponse;
import com.srini.demo.dto.OrderResponse;
import com.srini.demo.entity.*;
import com.srini.demo.exception.BadRequestException;
import com.srini.demo.exception.ResourceNotFoundException;
import com.srini.demo.kafka.event.OrderEvent;
import com.srini.demo.kafka.event.OrderEventType;
import com.srini.demo.kafka.producer.OrderEventProducer;
import com.srini.demo.repository.OrderRepository;
import com.srini.demo.repository.ProductRepository;
import com.srini.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository,
                        OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderEventProducer = orderEventProducer;
    }

    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product-by-id", allEntries = true)
    })
    public OrderResponse createOrder(String username, OrderCreateRequest request) {
        log.info("Creating order for user: {}", username);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        String orderNumber = "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        OrderEntity order = new OrderEntity(orderNumber, user.getId(), request.getShippingAddress());

        for (OrderItemRequest itemReq : request.getItems()) {
            ProductEntity product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemReq.getProductId()));

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new BadRequestException(String.format(
                        "Insufficient stock for product '%s'. Available: %d, Requested: %d",
                        product.getName(), product.getStockQuantity(), itemReq.getQuantity()
                ));
            }

            // Deduct inventory stock
            product.setStockQuantity(product.getStockQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            // Add order item
            OrderItemEntity orderItem = new OrderItemEntity(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    itemReq.getQuantity()
            );
            order.addItem(orderItem);
        }

        OrderEntity savedOrder = orderRepository.save(order);
        log.info("Order [{}] persisted with total: {}", savedOrder.getOrderNumber(), savedOrder.getTotalAmount());

        // Publish OrderPlaced event to Kafka for asynchronous downstream processing
        OrderEvent event = new OrderEvent(
                OrderEventType.ORDER_CREATED,
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getUserId(),
                savedOrder.getTotalAmount(),
                "New order created with " + savedOrder.getItems().size() + " items"
        );
        orderEventProducer.sendOrderEvent(event);

        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "orders", key = "#id")
    public OrderResponse getOrderById(Long id) {
        log.info("Fetching order by ID: {} (Cache Miss - querying database)", id);
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        List<OrderEntity> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return orders.stream().map(this::mapToResponse).toList();
    }

    @Caching(evict = {
            @CacheEvict(value = "orders", key = "#id"),
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product-by-id", allEntries = true)
    })
    public OrderResponse cancelOrder(Long id, String username) {
        log.info("User [{}] requested cancellation of order [{}]", username, id);

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (!order.getUserId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new BadRequestException("You are not authorized to cancel this order");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new BadRequestException("Completed orders cannot be cancelled");
        }

        // Restore inventory stock
        for (OrderItemEntity item : order.getItems()) {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            });
        }

        order.setStatus(OrderStatus.CANCELLED);
        OrderEntity updated = orderRepository.save(order);

        // Publish OrderCancelled event to Kafka
        OrderEvent event = new OrderEvent(
                OrderEventType.ORDER_CANCELLED,
                updated.getId(),
                updated.getOrderNumber(),
                updated.getUserId(),
                updated.getTotalAmount(),
                "Order cancelled by " + username
        );
        orderEventProducer.sendOrderEvent(event);

        return mapToResponse(updated);
    }

    public OrderResponse mapToResponse(OrderEntity order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getShippingAddress(),
                itemResponses,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
