package com.srini.demo.streams.model;

public record Product(
        Long id,
        String name,
        String category,
        double price,
        int stock
) {}
