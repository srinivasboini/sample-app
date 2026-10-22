package com.srini.demo.dto;

import com.srini.demo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing JWT token")
public class AuthResponse {

    @Schema(description = "JWT Access Token")
    private String token;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Authenticated username", example = "user")
    private String username;

    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Schema(description = "Assigned role", example = "ROLE_USER")
    private Role role;

    @Schema(description = "Token expiration in milliseconds", example = "86400000")
    private long expiresInMs;

    public AuthResponse() {
    }

    public AuthResponse(String token, String username, String email, Role role, long expiresInMs) {
        this.token = token;
        this.tokenType = "Bearer";
        this.username = username;
        this.email = email;
        this.role = role;
        this.expiresInMs = expiresInMs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getExpiresInMs() {
        return expiresInMs;
    }

    public void setExpiresInMs(long expiresInMs) {
        this.expiresInMs = expiresInMs;
    }
}
