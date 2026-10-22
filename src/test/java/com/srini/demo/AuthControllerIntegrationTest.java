package com.srini.demo;

import com.srini.demo.dto.AuthRequest;
import com.srini.demo.dto.RegisterRequest;
import com.srini.demo.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Should successfully authenticate existing admin user and return JWT")
    void testLoginSuccess() throws Exception {
        AuthRequest loginRequest = new AuthRequest("admin", "admin123");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.username").value("admin"))
                .andExpect(jsonPath("$.data.role").value("ROLE_ADMIN"));
    }

    @Test
    @DisplayName("Should reject authentication with invalid password")
    void testLoginFailure() throws Exception {
        AuthRequest loginRequest = new AuthRequest("admin", "wrongpassword");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("Should register a new user and allow login with new credentials")
    void testRegisterAndLogin() throws Exception {
        String uniqueUsername = "alice_" + System.currentTimeMillis();
        String uniqueEmail = uniqueUsername + "@example.com";

        RegisterRequest registerRequest = new RegisterRequest(
                uniqueUsername,
                uniqueEmail,
                "password123",
                Role.ROLE_USER
        );

        // 1. Register
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value(uniqueUsername))
                .andExpect(jsonPath("$.data.token").isNotEmpty());

        // 2. Login with newly created user
        AuthRequest loginRequest = new AuthRequest(uniqueUsername, "password123");
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value(uniqueUsername));
    }

    @Test
    @DisplayName("Should retrieve current user profile with valid JWT Bearer header")
    void testGetCurrentUserWithJwt() throws Exception {
        // Login to get token
        AuthRequest loginRequest = new AuthRequest("user", "password123");
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString())
                .path("data").path("token").asText();

        // Access protected /me endpoint
        mockMvc.perform(get("/api/v1/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("user"))
                .andExpect(jsonPath("$.data.email").value("user@orderflow.internal"));
    }

    @Test
    @DisplayName("Should return 401 when accessing protected endpoint without JWT")
    void testAccessMeWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/auth/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }
}
