package com.srini.demo.streams.model;

public record Transaction(
        Long id,
        int year,
        double value,
        Type type,
        String reference
) {
    public enum Type {
        DEBIT,
        CREDIT
    }
}
