package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String AUTHOR_KEY = "author";
    String TITLE_KEY = "title";
    String getKey();

    Specification<Book> getSpecification(String[] params);
}
