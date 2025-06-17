package com.example.demo.dto.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public record CreateBookRequestDto(
        @NotBlank(message = "Title is mandatory and cannot be blank")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        String title,
        @NotBlank(message = "Author is mandatory and cannot be blank")
        @Size(max = 100, message = "Author must not exceed 100 characters")
        String author,
        @NotBlank(message = "ISBN is mandatory and cannot be blank")
        @Size(max = 13, message = "ISBN must not exceed 13 characters")
        String isbn,
        @NotNull(message = "Price is mandatory")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,
        String coverImage,
        @NotEmpty
        Set<Long> categoryIds) {
}
