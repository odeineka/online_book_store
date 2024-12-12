package com.example.demo.repository.book.spec;

import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "title";
    }

    @Override
    public Specification<Book> getSpecification(String titlePart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder
                        .lower(root.get("title")), "%" + titlePart.toLowerCase() + "%");
    }
}

