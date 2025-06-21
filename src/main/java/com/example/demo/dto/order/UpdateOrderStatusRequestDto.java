package com.example.demo.dto.order;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderStatusRequestDto(
        @NotBlank(message = "Status is mandatory")
        String status) {
}
