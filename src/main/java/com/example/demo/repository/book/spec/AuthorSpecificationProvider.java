package com.example.demo.repository.book.spec;

import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "author";
    }

    @Override
    public Specification<Book> getSpecification(String authorPart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder
                        .lower(root.get("author")), "%" + authorPart.toLowerCase() + "%");
    }
}

