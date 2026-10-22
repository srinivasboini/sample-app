package com.srini.demo.controller;

import com.srini.demo.dto.ApiResponse;
import com.srini.demo.dto.OrderCreateRequest;
import com.srini.demo.dto.OrderResponse;
import com.srini.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Order processing APIs integrated with Apache Kafka event bus and Caffeine cache")
@SecurityRequirement(name = "BearerAuth")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Place a new customer order", description = "Validates inventory, reserves stock, creates an order in PENDING status, and asynchronously publishes an `ORDER_CREATED` event to Kafka")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Order created and event published"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Insufficient inventory stock or invalid items"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        OrderResponse response = orderService.createOrder(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "Order placed successfully. Processing asynchronously via Kafka event stream."));
    }

    @GetMapping
    @Operation(summary = "Get user order history", description = "Returns all orders placed by the currently authenticated user")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<OrderResponse> orders = orderService.getUserOrders(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok(orders, "User orders retrieved"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Fetches order details by ID with Caffeine cache acceleration")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order retrieved"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @Parameter(description = "Order primary key ID") @PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.ok(order, "Order retrieved successfully"));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order", description = "Cancels an order, restores inventory stock, and emits an `ORDER_CANCELLED` event to Kafka")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Order cannot be cancelled"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @Parameter(description = "Order primary key ID") @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        OrderResponse response = orderService.cancelOrder(id, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok(response, "Order cancelled successfully"));
    }
}
