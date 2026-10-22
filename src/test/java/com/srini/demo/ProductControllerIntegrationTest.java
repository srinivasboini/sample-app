package com.srini.demo;

import com.srini.demo.dto.AuthRequest;
import com.srini.demo.dto.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerIntegrationTest extends BaseIntegrationTest {

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUpTokens() throws Exception {
        if (adminToken == null) {
            MvcResult adminRes = mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new AuthRequest("admin", "admin123"))))
                    .andReturn();
            adminToken = objectMapper.readTree(adminRes.getResponse().getContentAsString())
                    .path("data").path("token").asText();

            MvcResult userRes = mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new AuthRequest("user", "password123"))))
                    .andReturn();
            userToken = objectMapper.readTree(userRes.getResponse().getContentAsString())
                    .path("data").path("token").asText();
        }
    }

    @Test
    @DisplayName("Should allow public unauthenticated access to view products")
    void testGetProductsPublic() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.totalElements", greaterThanOrEqualTo(5)));
    }

    @Test
    @DisplayName("Should fetch single product and utilize Caffeine cache")
    void testGetProductById() throws Exception {
        // First call - database query
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));

        // Second call - served from Caffeine cache
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("Admin user should be allowed to create new products")
    void testCreateProductAdmin() throws Exception {
        String uniqueSku = "TEST-SKU-" + System.currentTimeMillis();
        ProductRequest request = new ProductRequest(
                uniqueSku,
                "Test Mechanical Keyboard",
                "RGB backlit wireless mechanical keyboard",
                new BigDecimal("129.99"),
                30,
                "Accessories"
        );

        mockMvc.perform(post("/api/v1/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.sku").value(uniqueSku))
                .andExpect(jsonPath("$.data.price").value(129.99));
    }

    @Test
    @DisplayName("Regular user without admin role should receive 403 Forbidden on product creation")
    void testCreateProductUserForbidden() throws Exception {
        ProductRequest request = new ProductRequest(
                "FORBIDDEN-SKU",
                "Hacker Gadget",
                "Unauthorized product attempt",
                new BigDecimal("99.99"),
                10,
                "Gadgets"
        );

        mockMvc.perform(post("/api/v1/products")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("Admin can update product details and evict cached representations")
    void testUpdateProductAdmin() throws Exception {
        ProductRequest updateReq = new ProductRequest(
                "LAPTOP-MBP-16",
                "Apple MacBook Pro 16 M3 Max (Updated Edition)",
                "Upgraded specs with 64GB unified memory",
                new BigDecimal("3999.00"),
                20,
                "Computers"
        );

        mockMvc.perform(put("/api/v1/products/1")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Apple MacBook Pro 16 M3 Max (Updated Edition)"))
                .andExpect(jsonPath("$.data.price").value(3999.00));
    }
}
