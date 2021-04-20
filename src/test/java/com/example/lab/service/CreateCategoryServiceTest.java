package com.example.lab.service;

import com.example.lab.domain.Category;
import com.example.lab.domain.CategoryAttribute;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.service.dto.AttributeDto;
import com.example.lab.service.dto.AttributeType;
import com.example.lab.service.dto.CategoryDto;
import com.example.lab.service.impl.CategoryServiceImpl;
import com.example.lab.service.mapper.CategoryMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
class CreateCategoryServiceTest {
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
    void givenNewCategory_whenCreate_thenSuccessfullySaved() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        CategoryDto categoryDto = CategoryDto.builder()
                .name(categoryName)
                .description(categoryDescription)
                .attributes(new ArrayList<>())
                .build();
        CategoryDto categoryResponseDto = CategoryDto.builder()
                .id(1L)
                .name(categoryName)
                .description(categoryDescription)
                .attributes(new ArrayList<>())
                .build();

        Category category = mock(Category.class);
        final var categoryDomainAttributes = new ArrayList<CategoryAttribute>();
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(1L);

        given(categoryMapper.mapTwoWayRelationship(any(CategoryDto.class))).willReturn(category);
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        given(categoryMapper.map(any(Category.class))).willReturn(categoryResponseDto);


        final var actualCategoryResponseDto = categoryService.create(categoryDto);

        Assert.assertEquals(new ArrayList<>(), actualCategoryResponseDto.getAttributes());
        Assert.assertEquals(categoryDescription, actualCategoryResponseDto.getDescription());
        Assert.assertEquals(categoryName, actualCategoryResponseDto.getName());
        Assert.assertNotNull(actualCategoryResponseDto.getId());
        Assert.assertEquals(Long.valueOf(1), actualCategoryResponseDto.getId());

        then(categoryMapper).should().mapTwoWayRelationship(categoryDto);
        then(categoryRepository).should(only()).save(category);
        then(categoryMapper).should().map(category);
    }

    @Test
    void givenNewCategoryWithOneAttribute_whenCreate_thenSuccessfullySaved() {
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

        given(categoryMapper.mapTwoWayRelationship(any(CategoryDto.class))).willReturn(category);
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        given(categoryMapper.map(any(Category.class))).willReturn(categoryResponseDto);


        final var actualCategoryResponseDto = categoryService.create(categoryDto);

        Assert.assertEquals(attributes, actualCategoryResponseDto.getAttributes());
        Assert.assertEquals(categoryDescription, actualCategoryResponseDto.getDescription());
        Assert.assertEquals(categoryName, actualCategoryResponseDto.getName());
        Assert.assertNotNull(actualCategoryResponseDto.getId());
        Assert.assertEquals(Long.valueOf(1), actualCategoryResponseDto.getId());

        then(categoryMapper).should().mapTwoWayRelationship(categoryDto);
        then(categoryRepository).should(only()).save(category);
        then(categoryMapper).should().map(category);
    }

    @Test
    void givenNewCategoryWithTwoAttribute_whenCreate_thenSuccessfullySaved() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "age";
        final var att1Type = AttributeType.NUMBER;
        final var att2Name = "experimentName";
        final var att2Type = AttributeType.TEXT;
        final var attributes = Arrays.asList(
                AttributeDto.builder()
                        .name(att1Name)
                        .attributeType(att1Type)
                        .build(),
                AttributeDto.builder()
                        .name(att2Name)
                        .attributeType(att2Type)
                        .build()
        );
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
        final var categoryDomainAttributes = Arrays.asList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.NUMBER)
                        .build(),
                CategoryAttribute.builder()
                        .name(att2Name)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.TEXT)
                        .build()
        );
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(1L);

        given(categoryMapper.mapTwoWayRelationship(any(CategoryDto.class))).willReturn(category);
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        given(categoryMapper.map(any(Category.class))).willReturn(categoryResponseDto);

        final var actualCategoryResponseDto = categoryService.create(categoryDto);

        Assert.assertEquals(attributes, actualCategoryResponseDto.getAttributes());
        Assert.assertEquals(categoryDescription, actualCategoryResponseDto.getDescription());
        Assert.assertEquals(categoryName, actualCategoryResponseDto.getName());
        Assert.assertNotNull(actualCategoryResponseDto.getId());
        Assert.assertEquals(Long.valueOf(1), actualCategoryResponseDto.getId());

        then(categoryMapper).should().mapTwoWayRelationship(categoryDto);
        then(categoryRepository).should(only()).save(category);
        then(categoryMapper).should().map(category);
    }

}