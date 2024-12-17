package com.example.demo.repository.book.spec;

import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return SpecificationProvider.AUTHOR_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] authorParts) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String part : authorParts) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                        root.get(AUTHOR_KEY)), "%" + part.toLowerCase() + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
