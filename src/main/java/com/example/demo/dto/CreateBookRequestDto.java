package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Title is mandatory and cannot be blank")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;
    @NotBlank(message = "Author is mandatory and cannot be blank")
    @Size(max = 100, message = "Author must not exceed 100 characters")
    private String author;
    @NotBlank(message = "ISBN is mandatory and cannot be blank")
    @Size(max = 13, message = "ISBN must not exceed 13 characters")
    private String isbn;
    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    private String coverImage;
}
