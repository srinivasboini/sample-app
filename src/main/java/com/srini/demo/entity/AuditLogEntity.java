package com.srini.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_order_id", columnList = "orderId"),
    @Index(name = "idx_audit_event_type", columnList = "eventType")
})
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String eventId;

    @Column(nullable = false, length = 50)
    private String eventType;

    @Column(nullable = false)
    private Long orderId;

    @Column(length = 64)
    private String orderNumber;

    @Column(nullable = false, length = 2000)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime processedAt;

    @Column(length = 100)
    private String consumerGroup;

    public AuditLogEntity() {
    }

    public AuditLogEntity(String eventId, String eventType, Long orderId, String orderNumber, String payload, String consumerGroup) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.payload = payload;
        this.consumerGroup = consumerGroup;
        this.processedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLogEntity that = (AuditLogEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId);
    }
}
