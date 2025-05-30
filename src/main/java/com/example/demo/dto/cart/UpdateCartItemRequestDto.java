package com.example.demo.dto.cart;

import jakarta.validation.constraints.Positive;

public record UpdateCartItemRequestDto(@Positive int quantity) {
}
