package com.example.demo.dto.cart;

import java.util.List;

public record ShoppingCartResponseDto(Long id, Long userId, List<CartItemResponseDto> cartItems) {
}
