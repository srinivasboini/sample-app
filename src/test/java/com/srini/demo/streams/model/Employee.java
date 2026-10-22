package com.srini.demo.streams.model;

import java.util.List;

public record Employee(
        Long id,
        String name,
        String department,
        double salary,
        String gender,
        int age,
        List<String> skills,
        String city
) {}
