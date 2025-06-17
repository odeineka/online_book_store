package com.example.demo.dto.cart;

public record CartItemResponseDto(Long id, Long bookId, String bookTitle, int quantity) {
}
