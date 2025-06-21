package com.example.demo.dto.order;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequestDto(
        @NotBlank(message = "Shipping address is mandatory")
        String shippingAddress
) {
}
