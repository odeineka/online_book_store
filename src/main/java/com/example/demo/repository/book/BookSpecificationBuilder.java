package com.example.demo.repository.book;

import static com.example.demo.repository.SpecificationProvider.AUTHOR_KEY;
import static com.example.demo.repository.SpecificationProvider.TITLE_KEY;

import com.example.demo.dto.book.BookSearchParametersDto;
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

        if (searchParameters.authorParts() != null && searchParameters.authorParts().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecification(AUTHOR_KEY)
                    .getSpecification(searchParameters.authorParts()));
        }
        if (searchParameters.titleParts() != null && searchParameters.titleParts().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecification(TITLE_KEY)
                    .getSpecification(searchParameters.titleParts()));
        }
        return spec;
    }
}
