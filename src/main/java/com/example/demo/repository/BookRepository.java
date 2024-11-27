package com.example.demo.repository;

import com.example.demo.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAll();

    Optional<Book> findBookById(Long id);

    Book createBook(Book book);
}
