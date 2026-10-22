package com.srini.demo.controller;

import com.srini.demo.dto.ApiResponse;
import com.srini.demo.dto.ProductRequest;
import com.srini.demo.dto.ProductResponse;
import com.srini.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product catalog CRUD APIs with Caffeine in-memory caching")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @SecurityRequirements
    @Operation(summary = "Get paginated products", description = "Returns a paginated list of catalog products, optionally filtered by category")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(
            @Parameter(description = "Filter by product category") @RequestParam(required = false) String category,
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort property") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductResponse> products = productService.getAllProducts(category, pageable);
        return ResponseEntity.ok(ApiResponse.ok(products, "Products retrieved successfully"));
    }

    @GetMapping("/{id}")
    @SecurityRequirements
    @Operation(summary = "Get product by ID", description = "Fetches a product by its ID. Subsequent queries are served directly from the Caffeine cache.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @Parameter(description = "Product primary key ID") @PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.ok(product, "Product retrieved successfully"));
    }

    @PostMapping
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Create a product (Admin only)", description = "Creates a new catalog product and evicts stale product caches")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Duplicate SKU or invalid parameters"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin privileges required")
    })
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "Product created successfully"));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Update a product (Admin only)", description = "Updates an existing product and evicts cache entries for that product")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin privileges required")
    })
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Delete a product (Admin only)", description = "Deletes a product from the database and purges it from cache")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin privileges required")
    })
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Product deleted successfully"));
    }
}
