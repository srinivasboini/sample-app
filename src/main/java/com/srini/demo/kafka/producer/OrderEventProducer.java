package com.srini.demo.kafka.producer;

import com.srini.demo.kafka.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);
    public static final String ORDER_TOPIC = "order-events";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, OrderEvent>> sendOrderEvent(OrderEvent event) {
        log.info("Publishing Kafka event [type={}, orderId={}, eventId={}] to topic: {}",
                event.getEventType(), event.getOrderId(), event.getEventId(), ORDER_TOPIC);

        CompletableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(
                ORDER_TOPIC,
                event.getOrderNumber(),
                event
        );

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Kafka event sent successfully: [topic={}, partition={}, offset={}, eventId={}]",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        event.getEventId());
            } else {
                log.error("Failed to send Kafka event [eventId={}]: {}", event.getEventId(), ex.getMessage(), ex);
            }
        });

        return future;
    }
}
