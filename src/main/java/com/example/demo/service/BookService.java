package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    BookDto findBookById(Long id);

    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void softDeleteBookById(Long id);
}
