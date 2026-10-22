package com.srini.demo;

import com.srini.demo.dto.AuthRequest;
import com.srini.demo.dto.OrderCreateRequest;
import com.srini.demo.dto.OrderItemRequest;
import com.srini.demo.entity.OrderEntity;
import com.srini.demo.entity.OrderStatus;
import com.srini.demo.repository.AuditLogRepository;
import com.srini.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderKafkaIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    private String userToken;

    @BeforeEach
    void setUpUserToken() throws Exception {
        if (userToken == null) {
            MvcResult userRes = mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new AuthRequest("user", "password123"))))
                    .andExpect(status().isOk())
                    .andReturn();
            userToken = objectMapper.readTree(userRes.getResponse().getContentAsString())
                    .path("data").path("token").asText();
        }
    }

    @Test
    @DisplayName("Place order -> publish Kafka event -> Consumer confirms order and writes audit log")
    void testEndToEndOrderKafkaFlow() throws Exception {
        OrderCreateRequest orderRequest = new OrderCreateRequest(
                "8 Marina View, Asia Square Tower 1, Singapore",
                List.of(
                        new OrderItemRequest(2L, 1), // iPhone 15 Pro Max
                        new OrderItemRequest(3L, 2)  // Sony Headphones x 2
                )
        );

        // 1. Post Order via REST endpoint
        MvcResult orderResult = mockMvc.perform(post("/api/v1/orders")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderNumber").isNotEmpty())
                .andReturn();

        Long orderId = objectMapper.readTree(orderResult.getResponse().getContentAsString())
                .path("data").path("id").asLong();

        assertThat(orderId).isNotNull();

        // 2. Wait for Kafka consumer to process the ORDER_CREATED event asynchronously
        await().atMost(Duration.ofSeconds(10))
                .pollInterval(200, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    OrderEntity order = orderRepository.findById(orderId).orElse(null);
                    assertThat(order).isNotNull();
                    assertThat(order.getStatus()).isEqualTo(OrderStatus.CONFIRMED);

                    var auditLogs = auditLogRepository.findByOrderIdOrderByProcessedAtDesc(orderId);
                    assertThat(auditLogs).isNotEmpty();
                    assertThat(auditLogs.get(0).getEventType()).isEqualTo("ORDER_CREATED");
                });

        // 3. Verify Kafka Audit Logs REST Endpoint
        mockMvc.perform(get("/api/v1/events/order/" + orderId)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.data[0].orderId").value(orderId));

        // 4. Cancel the order and verify cancellation flow
        mockMvc.perform(patch("/api/v1/orders/" + orderId + "/cancel")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("CANCELLED"));

        // Wait for cancellation audit log in Kafka
        await().atMost(Duration.ofSeconds(10))
                .pollInterval(200, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    var logs = auditLogRepository.findByOrderIdOrderByProcessedAtDesc(orderId);
                    assertThat(logs.stream().anyMatch(l -> "ORDER_CANCELLED".equals(l.getEventType()))).isTrue();
                });
    }
}
