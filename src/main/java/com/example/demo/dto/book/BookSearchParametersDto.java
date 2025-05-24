package com.example.demo.dto.book;

public record BookSearchParametersDto(String[] titleParts,
                                      String[] authorParts,
                                      String[] categoryIds) {
}
