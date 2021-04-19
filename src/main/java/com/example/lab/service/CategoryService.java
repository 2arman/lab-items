package com.example.lab.service;

import com.example.lab.service.dto.CategoriesDto;
import com.example.lab.service.dto.CategoryDto;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 9:51 AM
 **/
public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto getById(Long categoryId);

    CategoriesDto getAll();
}
