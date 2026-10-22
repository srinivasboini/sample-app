package com.srini.demo.streams.model;

public record Customer(
        Long id,
        String name,
        String email,
        CustomerTier tier
) {}
