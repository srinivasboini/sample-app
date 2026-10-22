package com.srini.demo.streams.model;

import java.time.LocalDate;
import java.util.List;

public record Order(
        Long id,
        String orderNumber,
        Customer customer,
        List<OrderItem> items,
        LocalDate orderDate,
        OrderStatus status
) {
    public double getTotalAmount() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }
}
