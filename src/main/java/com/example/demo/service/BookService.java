package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookSearchParametersDto;
import com.example.demo.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {

    List<BookDto> getAll(Pageable pageable);

    BookDto findBookById(Long id);

    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void softDeleteBookById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable);
}
