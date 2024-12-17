package com.example.demo.repository.book.spec;

import com.example.demo.model.Book;
import com.example.demo.repository.SpecificationProvider;
import com.example.demo.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecification(String key) {
        return bookSpecificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find correct specification"
                        + " provider for key: " + key));
    }
}
