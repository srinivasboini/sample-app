package com.srini.demo;

import com.srini.demo.dto.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CacheManagementIntegrationTest extends BaseIntegrationTest {

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
    @DisplayName("Admin can fetch cache statistics")
    void testGetCacheStatsAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/cache/stats")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(3))));
    }

    @Test
    @DisplayName("Regular user cannot inspect cache statistics (403 Forbidden)")
    void testGetCacheStatsUserForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/cache/stats")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("Admin can trigger manual cache eviction")
    void testClearCacheAdmin() throws Exception {
        mockMvc.perform(post("/api/v1/cache/clear")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("cacheName", "products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Cache 'products' cleared successfully"));
    }
}
