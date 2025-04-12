package com.example.demo.service.impl;

import com.example.demo.dto.category.CategoryResponseDto;
import com.example.demo.dto.category.CreateCategoryRequestDto;
import com.example.demo.exception.DataProcessingException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.Category;
import com.example.demo.repository.category.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Category not found with id %d", id)));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        categoryRepository.findByName(requestDto.name())
                .filter(cat -> !cat.isDeleted())
                .ifPresent(cat -> {
                    throw new DataProcessingException(String.format(
                            "Category with name '%s' already exists", requestDto.name()));
                });
        Category category = categoryMapper.toEntity(requestDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
        categoryMapper.updateCategoryFromDto(requestDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("Category not found with id %d", id)
            );
        }
        categoryRepository.deleteById(id);
    }
}
