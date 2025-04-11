package com.example.demo.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(@NotBlank String name, String description) {

}
