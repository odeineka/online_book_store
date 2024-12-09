package com.example.demo.repository;

import com.example.demo.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.isDeleted = false")
    List<Book> findAllActive();

    @Query("SELECT b FROM Book b WHERE b.id = :id AND b.isDeleted = false")
    Optional<Book> findActiveById(@Param("id") Long id);
}
