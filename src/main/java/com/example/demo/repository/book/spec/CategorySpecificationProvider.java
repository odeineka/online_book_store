package com.example.demo.repository.book.spec;

import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return SpecificationProvider.CATEGORY_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] categoryNames) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<Object, Object> categories = root.join(CATEGORY_KEY, JoinType.INNER);
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(categories.get("name"));
            for (String name : categoryNames) {
                inClause.value(name);
            }
            return inClause;
        };
    }
}
