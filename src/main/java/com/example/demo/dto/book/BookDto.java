package com.example.demo.dto.book;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public record BookDto(Long id, String title, String author, String isbn, BigDecimal price,
                      String description, String coverImage, Set<Long> categoryIds) {
    public BookDto {
        if (categoryIds == null) {
            categoryIds = new HashSet<>();
        }
    }
}
