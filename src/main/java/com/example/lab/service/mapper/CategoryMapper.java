package com.example.lab.service.mapper;

import com.example.lab.domain.Category;
import com.example.lab.service.dto.CategoriesDto;
import com.example.lab.service.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 6:11 PM
 **/
@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
    default CategoriesDto mapList(List<Category> categories) {
        return CategoriesDto.builder()
                .categories(map(categories))
                .build();
    }

    List<CategoryDto> map(List<Category> categories);

    Category map(CategoryDto categoryDto);

    CategoryDto map(Category category);
}
