package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.book.BookDto;
import com.example.demo.dto.book.BookWithoutCategoryIdsDto;
import com.example.demo.dto.book.CreateBookRequestDto;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", config = MapperConfig.class, uses = { CategoryMapper.class })
public interface BookMapper {
    BookDto toDto(Book book);

    BookWithoutCategoryIdsDto toDtoWithoutCategoryIds(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book entity);

    @AfterMapping
    default void mapCategoryIdsToEntities(CreateBookRequestDto dto, @MappingTarget Book book) {
        if (dto.categoryIds() != null) {
            book.setCategories(dto.categoryIds().stream()
                    .map(Category::new)
                    .collect(Collectors.toSet()));
        }
    }

    @AfterMapping
    default void mapEntitiesToCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.categoryIds().addAll(
                    book.getCategories().stream()
                            .map(Category::getId)
                            .collect(Collectors.toSet())
            );
        }
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        return id == null ? null : new Book(id);
    }
}
