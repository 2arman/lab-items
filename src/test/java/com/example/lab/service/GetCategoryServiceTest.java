package com.example.lab.service;

import com.example.lab.domain.Category;
import com.example.lab.domain.CategoryAttribute;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.service.dto.AttributeDto;
import com.example.lab.service.dto.AttributeType;
import com.example.lab.service.dto.CategoriesDto;
import com.example.lab.service.dto.CategoryDto;
import com.example.lab.service.exception.CategoryNotFoundException;
import com.example.lab.service.impl.CategoryServiceImpl;
import com.example.lab.service.mapper.CategoryMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 10:25 AM
 **/
class GetCategoryServiceTest {
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryMapper = mock(CategoryMapper.class);
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void givenValidCategory_WhenGetById_thenSuccessfullyReturn() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "factorX";
        final var att1Type = AttributeType.DOUBLE;

        final List<AttributeDto> attributes = Collections.singletonList(
                AttributeDto.builder()
                        .name(att1Name)
                        .attributeType(att1Type)
                        .build());

        CategoryDto categoryResponseDto = CategoryDto.builder()
                .id(1L)
                .name(categoryName)
                .description(categoryDescription)
                .attributes(attributes)
                .build();

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(1L);

        given(categoryMapper.map(any(CategoryDto.class))).willReturn(category);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(categoryMapper.map(any(Category.class))).willReturn(categoryResponseDto);


        final var actualCategoryResponseDto = categoryService.getById(1L);

        Assert.assertEquals(attributes, actualCategoryResponseDto.getAttributes());
        Assert.assertEquals(categoryDescription, actualCategoryResponseDto.getDescription());
        Assert.assertEquals(categoryName, actualCategoryResponseDto.getName());
        Assert.assertNotNull(actualCategoryResponseDto.getId());
        Assert.assertEquals(Long.valueOf(1), actualCategoryResponseDto.getId());

        then(categoryRepository).should(only()).findById(1L);
        then(categoryMapper).should(only()).map(category);
    }

    @Test
    void givenInvalidCategory_WhenGetById_thenFailedThrowCategoryNotFoundException() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "factorX";
        final var att1Type = AttributeType.DOUBLE;

        final List<AttributeDto> attributes = Collections.singletonList(
                AttributeDto.builder()
                        .name(att1Name)
                        .attributeType(att1Type)
                        .build());

        CategoryDto categoryDto = CategoryDto.builder()
                .name(categoryName)
                .description(categoryDescription)
                .attributes(attributes)
                .build();

        CategoryDto categoryResponseDto = CategoryDto.builder()
                .id(1L)
                .name(categoryName)
                .description(categoryDescription)
                .attributes(attributes)
                .build();

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(1L);

        given(categoryMapper.map(any(CategoryDto.class))).willReturn(category);
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        given(categoryMapper.map(any(Category.class))).willReturn(categoryResponseDto);


        Assertions.assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getById(1L));

    }

    @Test
    void givenOneCategory_WhenGetAll_thenSuccessfullyReturnCategory() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "factorX";
        final var att1Type = AttributeType.DOUBLE;

        final List<AttributeDto> attributes = Collections.singletonList(
                AttributeDto.builder()
                        .name(att1Name)
                        .attributeType(att1Type)
                        .build());

        CategoryDto categoryResponseDto = CategoryDto.builder()
                .id(1L)
                .name(categoryName)
                .description(categoryDescription)
                .attributes(attributes)
                .build();

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(1L);

        final var categories = Collections.singletonList(category);
        given(categoryRepository.findAll()).willReturn(categories);
        CategoriesDto categoriesResponseDto = CategoriesDto.builder()
                .categories(Collections.singletonList(categoryResponseDto))
                .build();
        given(categoryMapper.mapList(categories)).willReturn(categoriesResponseDto);


        final var actualCategoryResponseDto = categoryService.getAll();

        Assert.assertEquals(1, actualCategoryResponseDto.getCategories().size());
        Assert.assertEquals(attributes, actualCategoryResponseDto.getCategories().get(0).getAttributes());
        Assert.assertEquals(categoryDescription, actualCategoryResponseDto.getCategories().get(0).getDescription());
        Assert.assertEquals(categoryName, actualCategoryResponseDto.getCategories().get(0).getName());
        Assert.assertNotNull(actualCategoryResponseDto.getCategories().get(0).getId());
        Assert.assertEquals(Long.valueOf(1), actualCategoryResponseDto.getCategories().get(0).getId());

        then(categoryRepository).should(only()).findAll();
        then(categoryMapper).should(only()).mapList(categories);
    }

    @Test
    void givenTwoCategory_WhenGetAll_thenSuccessfullyReturnBothCategory() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var categoryName2 = "sample_category2";
        final var categoryDescription2 = "this is attributes sample category2";
        final var att1Name = "factorX";
        final var att1Type = AttributeType.DOUBLE;
        final var att2Name = "experimentName";
        final var att2Type = AttributeType.TEXT;

        final List<AttributeDto> attributes = Collections.singletonList(
                AttributeDto.builder()
                        .name(att1Name)
                        .attributeType(att1Type)
                        .build());

        final List<AttributeDto> attributes2 = Collections.singletonList(
                AttributeDto.builder()
                        .name(att2Name)
                        .attributeType(att2Type)
                        .build());

        CategoryDto categoryResponseDto = CategoryDto.builder()
                .id(1L)
                .name(categoryName)
                .description(categoryDescription)
                .attributes(attributes)
                .build();

        CategoryDto categoryResponseDto2 = CategoryDto.builder()
                .id(2L)
                .name(categoryName2)
                .description(categoryDescription2)
                .attributes(attributes2)
                .build();

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(1L);

        Category category2 = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes2 = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        given(category2.getName()).willReturn(categoryName2);
        given(category2.getDescription()).willReturn(categoryDescription2);
        given(category2.getAttributes()).willReturn(categoryDomainAttributes2);
        given(category2.getId()).willReturn(2L);

        final List<Category> categories = List.of(category, category2);
        given(categoryRepository.findAll()).willReturn(categories);
        CategoriesDto categoriesResponseDto = CategoriesDto.builder()
                .categories(List.of(categoryResponseDto, categoryResponseDto2))
                .build();
        given(categoryMapper.mapList(categories)).willReturn(categoriesResponseDto);


        final var actualCategoryResponseDto = categoryService.getAll();

        Assert.assertEquals(2, actualCategoryResponseDto.getCategories().size());
        Assert.assertEquals(attributes, actualCategoryResponseDto.getCategories().get(0).getAttributes());
        Assert.assertEquals(categoryDescription, actualCategoryResponseDto.getCategories().get(0).getDescription());
        Assert.assertEquals(categoryName, actualCategoryResponseDto.getCategories().get(0).getName());
        Assert.assertNotNull(actualCategoryResponseDto.getCategories().get(0).getId());
        Assert.assertEquals(Long.valueOf(1), actualCategoryResponseDto.getCategories().get(0).getId());

        Assert.assertEquals(attributes2, actualCategoryResponseDto.getCategories().get(1).getAttributes());
        Assert.assertEquals(categoryDescription2, actualCategoryResponseDto.getCategories().get(1).getDescription());
        Assert.assertEquals(categoryName2, actualCategoryResponseDto.getCategories().get(1).getName());
        Assert.assertNotNull(actualCategoryResponseDto.getCategories().get(1).getId());
        Assert.assertEquals(Long.valueOf(2), actualCategoryResponseDto.getCategories().get(1).getId());

        then(categoryRepository).should(only()).findAll();
        then(categoryMapper).should(only()).mapList(categories);
    }


}