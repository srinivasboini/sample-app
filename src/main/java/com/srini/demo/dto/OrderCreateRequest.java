package com.srini.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Order checkout payload")
public class OrderCreateRequest {

    @Schema(description = "Delivery destination address", example = "10 Marina Boulevard, #28-01, Marina Bay Financial Centre, Singapore")
    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;

    @Schema(description = "Ordered item lines")
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemRequest> items;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(String shippingAddress, List<OrderItemRequest> items) {
        this.shippingAddress = shippingAddress;
        this.items = items;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
