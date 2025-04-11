package com.example.demo.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryResponseDto(Long id, @NotBlank(message = "Name is required") String name,
                                  String description) {
}
