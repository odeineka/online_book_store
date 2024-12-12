package com.example.demo.repository.book;

import com.example.demo.dto.BookSearchParametersDto;
import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationBuilder;
import com.example.demo.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);

        if (searchParameters.authorPart() != null && !searchParameters.authorPart().isEmpty()) {
            spec = spec.and(bookSpecificationProviderManager.getSpecification("author")
                    .getSpecification(searchParameters.authorPart()));
        }
        if (searchParameters.titlePart() != null && !searchParameters.titlePart().isEmpty()) {
            spec = spec.and(bookSpecificationProviderManager.getSpecification("title")
                    .getSpecification(searchParameters.titlePart()));
        }
        return spec;
    }
}
