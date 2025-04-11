package com.example.demo.service;

import com.example.demo.dto.book.BookDto;
import com.example.demo.dto.book.BookSearchParametersDto;
import com.example.demo.dto.book.CreateBookRequestDto;
import com.example.demo.model.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    List<BookDto> getAll(Pageable pageable);

    BookDto findBookById(Long id);

    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void softDeleteBookById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable);

    Page<Book> findBooksByCategoryId(Long categoryId, Pageable pageable);
}
