package com.example.demo.dto.cart;

import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(@Positive Long bookId, @Positive int quantity) {
}
