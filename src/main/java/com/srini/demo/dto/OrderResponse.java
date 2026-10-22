package com.srini.demo.dto;

import com.srini.demo.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Customer order response")
public class OrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Internal database ID", example = "1")
    private Long id;

    @Schema(description = "Order public identifier", example = "ORD-20261022-837194")
    private String orderNumber;

    @Schema(description = "Buyer User ID", example = "2")
    private Long userId;

    @Schema(description = "Current lifecycle state of the order", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Total cost of order", example = "4999.98")
    private BigDecimal totalAmount;

    @Schema(description = "Delivery destination", example = "10 Marina Boulevard, Singapore")
    private String shippingAddress;

    @Schema(description = "Purchased items")
    private List<OrderItemResponse> items;

    @Schema(description = "Created timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last modified timestamp")
    private LocalDateTime updatedAt;

    public OrderResponse() {
    }

    public OrderResponse(Long id, String orderNumber, Long userId, OrderStatus status, BigDecimal totalAmount, String shippingAddress, List<OrderItemResponse> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
