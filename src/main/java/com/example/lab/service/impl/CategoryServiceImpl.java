package com.example.lab.service.impl;

import com.example.lab.repository.CategoryRepository;
import com.example.lab.service.CategoryService;
import com.example.lab.service.dto.CategoriesDto;
import com.example.lab.service.dto.CategoryDto;
import com.example.lab.service.exception.CategoryNotFoundException;
import com.example.lab.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 6:10 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return categoryMapper.map(categoryRepository.save(categoryMapper.map(categoryDto)));
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        return categoryMapper.map(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("The category not found!")));
    }

    @Override
    public CategoriesDto getAll() {
        return categoryMapper.mapList(categoryRepository.findAll());
    }
}
