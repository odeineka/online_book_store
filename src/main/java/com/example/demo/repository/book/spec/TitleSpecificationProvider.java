package com.example.demo.repository.book.spec;

import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return SpecificationProvider.TITLE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] titleParts) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String part : titleParts) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(TITLE_KEY)),
                        "%" + part.toLowerCase() + "%"
                ));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
