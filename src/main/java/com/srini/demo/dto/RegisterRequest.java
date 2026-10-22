package com.srini.demo.dto;

import com.srini.demo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "User registration payload")
public class RegisterRequest {

    @Schema(description = "Unique username", example = "john_doe")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Schema(description = "Valid user email address", example = "john@example.com")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email format")
    private String email;

    @Schema(description = "User password (minimum 6 characters)", example = "SecurePass123!")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "User role", example = "ROLE_USER")
    private Role role = Role.ROLE_USER;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : Role.ROLE_USER;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
