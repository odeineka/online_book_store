package com.example.demo.dto.cart;

import jakarta.validation.constraints.Min;

public record UpdateCartItemRequestDto(@Min(1) int quantity) {
}
