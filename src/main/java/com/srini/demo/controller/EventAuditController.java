package com.srini.demo.controller;

import com.srini.demo.dto.ApiResponse;
import com.srini.demo.entity.AuditLogEntity;
import com.srini.demo.service.EventAuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Kafka Audit Events", description = "Query real-time audit logs created by Kafka consumer processing")
@SecurityRequirement(name = "BearerAuth")
public class EventAuditController {

    private final EventAuditService eventAuditService;

    public EventAuditController(EventAuditService eventAuditService) {
        this.eventAuditService = eventAuditService;
    }

    @GetMapping
    @Operation(summary = "Get all Kafka processed events", description = "Returns a paginated list of all events received and processed by the Kafka consumer")
    public ResponseEntity<ApiResponse<Page<AuditLogEntity>>> getAllAuditLogs(
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AuditLogEntity> logs = eventAuditService.getAllAuditLogs(pageable);
        return ResponseEntity.ok(ApiResponse.ok(logs, "Audit logs retrieved successfully"));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get audit logs for an order", description = "Returns all Kafka events associated with a specific order")
    public ResponseEntity<ApiResponse<List<AuditLogEntity>>> getAuditLogsForOrder(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        List<AuditLogEntity> logs = eventAuditService.getAuditLogsForOrder(orderId);
        return ResponseEntity.ok(ApiResponse.ok(logs, "Order event history retrieved"));
    }
}
