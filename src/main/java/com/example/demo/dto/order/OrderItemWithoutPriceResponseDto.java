package com.example.demo.dto.order;

public record OrderItemWithoutPriceResponseDto(Long id, Long bookId, int quantity) {
}
