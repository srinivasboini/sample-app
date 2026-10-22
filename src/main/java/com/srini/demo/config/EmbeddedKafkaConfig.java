package com.srini.demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

@Configuration
@ConditionalOnProperty(name = "app.kafka.embedded.enabled", havingValue = "true", matchIfMissing = true)
public class EmbeddedKafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedKafkaConfig.class);

    public static final String TOPIC_ORDER_EVENTS = "order-events";

    @Value("${app.kafka.embedded.port:9092}")
    private int port;

    private EmbeddedKafkaKraftBroker broker;

    @PostConstruct
    public void startBroker() {
        log.info("==================================================================");
        log.info("Starting local Embedded Kafka (KRaft mode) on port: {}...", port);
        log.info("==================================================================");

        this.broker = new EmbeddedKafkaKraftBroker(1, 1, TOPIC_ORDER_EVENTS);
        this.broker.kafkaPorts(port);
        this.broker.afterPropertiesSet();

        log.info("Embedded Kafka (KRaft) broker successfully started at: {}", broker.getBrokersAsString());
    }

    @Bean
    @Primary
    public EmbeddedKafkaBroker embeddedKafkaBroker() {
        return this.broker;
    }

    @PreDestroy
    public void stopBroker() {
        if (this.broker != null) {
            log.info("Stopping Embedded Kafka broker...");
            this.broker.destroy();
            log.info("Embedded Kafka broker stopped.");
        }
    }
}
