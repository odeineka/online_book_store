package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.category.CategoryDto;
import com.example.demo.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);

    void updateCategoryFromDto(CategoryDto dto, @MappingTarget Category category);
}
