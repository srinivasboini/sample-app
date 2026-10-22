package com.srini.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Product details response")
public class ProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Unique SKU", example = "LAPTOP-PRO-16")
    private String sku;

    @Schema(description = "Product name", example = "MacBook Pro 16-inch M3 Max")
    private String name;

    @Schema(description = "Detailed product description", example = "Apple M3 Max chip, 36GB unified memory, 1TB SSD")
    private String description;

    @Schema(description = "Unit price", example = "2499.99")
    private BigDecimal price;

    @Schema(description = "Stock quantity", example = "50")
    private Integer stockQuantity;

    @Schema(description = "Category", example = "Electronics")
    private String category;

    @Schema(description = "Created timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last modified timestamp")
    private LocalDateTime updatedAt;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String sku, String name, String description, BigDecimal price, Integer stockQuantity, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
