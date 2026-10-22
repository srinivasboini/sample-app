package com.srini.demo.streams.model;

public record OrderItem(
        Product product,
        int quantity
) {
    public double getSubtotal() {
        return product.price() * quantity;
    }
}
