package com.srini.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Product creation or update payload")
public class ProductRequest {

    @Schema(description = "Unique SKU code", example = "LAPTOP-PRO-16")
    @NotBlank(message = "SKU cannot be blank")
    private String sku;

    @Schema(description = "Product title", example = "MacBook Pro 16-inch M3 Max")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Schema(description = "Product detailed description", example = "Apple M3 Max chip, 36GB unified memory, 1TB SSD")
    private String description;

    @Schema(description = "Product unit price", example = "2499.99")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Available inventory stock quantity", example = "50")
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @Schema(description = "Product category", example = "Electronics")
    @NotBlank(message = "Category cannot be blank")
    private String category;

    public ProductRequest() {
    }

    public ProductRequest(String sku, String name, String description, BigDecimal price, Integer stockQuantity, String category) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
