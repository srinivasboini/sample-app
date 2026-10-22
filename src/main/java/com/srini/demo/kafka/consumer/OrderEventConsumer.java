package com.srini.demo.kafka.consumer;

import com.srini.demo.entity.AuditLogEntity;
import com.srini.demo.entity.OrderEntity;
import com.srini.demo.entity.OrderStatus;
import com.srini.demo.kafka.event.OrderEvent;
import com.srini.demo.kafka.event.OrderEventType;
import com.srini.demo.repository.AuditLogRepository;
import com.srini.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.Optional;

@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final OrderRepository orderRepository;
    private final AuditLogRepository auditLogRepository;

    public OrderEventConsumer(OrderRepository orderRepository, AuditLogRepository auditLogRepository) {
        this.orderRepository = orderRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @KafkaListener(topics = "order-events", groupId = "order-processing-group")
    @Transactional
    public void consumeOrderEvent(
            @Payload OrderEvent event,
            @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) Integer partition,
            @Header(value = KafkaHeaders.OFFSET, required = false) Long offset) {

        log.info("Received Kafka event [type={}, eventId={}, orderNumber={}] from partition: {}, offset: {}",
                event.getEventType(), event.getEventId(), event.getOrderNumber(), partition, offset);

        // 1. Record Audit Log for Event
        AuditLogEntity auditLog = new AuditLogEntity(
                event.getEventId(),
                event.getEventType().name(),
                event.getOrderId(),
                event.getOrderNumber(),
                event.toString(),
                "order-processing-group"
        );
        auditLogRepository.save(auditLog);

        // 2. Perform business workflow state transition
        if (event.getEventType() == OrderEventType.ORDER_CREATED) {
            Optional<OrderEntity> orderOpt = orderRepository.findById(event.getOrderId());
            if (orderOpt.isPresent()) {
                OrderEntity order = orderOpt.get();
                if (order.getStatus() == OrderStatus.PENDING) {
                    order.setStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(order);
                    log.info("Order [{}] successfully confirmed via Kafka async event processing!", order.getOrderNumber());
                }
            } else {
                log.warn("Order not found with id: {} for Kafka event processing", event.getOrderId());
            }
        }
    }
}
