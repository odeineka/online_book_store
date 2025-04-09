package com.example.demo.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(Long id, @NotBlank(message = "Name is required") String name,
                           String description) {
}
