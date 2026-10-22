package com.srini.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Order item details")
public class OrderItemResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Line item ID", example = "10")
    private Long id;

    @Schema(description = "Product ID", example = "1")
    private Long productId;

    @Schema(description = "Product name at purchase", example = "MacBook Pro 16-inch M3 Max")
    private String productName;

    @Schema(description = "Unit price at purchase", example = "2499.99")
    private BigDecimal unitPrice;

    @Schema(description = "Purchased quantity", example = "2")
    private Integer quantity;

    @Schema(description = "Line item total amount", example = "4999.98")
    private BigDecimal subtotal;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long id, Long productId, String productName, BigDecimal unitPrice, Integer quantity, BigDecimal subtotal) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
