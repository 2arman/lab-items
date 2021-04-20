package com.example.lab.service;

import com.example.lab.domain.Category;
import com.example.lab.domain.CategoryAttribute;
import com.example.lab.domain.Item;
import com.example.lab.domain.ItemAttributeValue;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.repository.ItemRepository;
import com.example.lab.service.dto.AttributeDto;
import com.example.lab.service.dto.AttributeType;
import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.exception.CategoryNotFoundException;
import com.example.lab.service.exception.ItemNotFoundException;
import com.example.lab.service.impl.ItemServiceImpl;
import com.example.lab.service.mapper.ItemMapper;
import com.example.lab.service.validation.AttributeValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
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
 * Time: 11:46 PM
 **/
class UpdateItemServiceTest {

    private ItemService itemService;
    private ItemRepository itemRepository;
    private ItemMapper itemMapper;
    private CategoryRepository categoryRepository;
    private AttributeValidator attributeValidator;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        itemRepository = mock(ItemRepository.class);
        itemMapper = mock(ItemMapper.class);
        attributeValidator = mock(AttributeValidator.class);
        itemService = new ItemServiceImpl(categoryRepository, itemRepository, itemMapper, attributeValidator);
    }

    @Test
    void givenValidItemWithOneAttributeValue_whenUpdate_thenSuccessfullySaved() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "factorX";
        final var att1Type = AttributeType.DOUBLE;

        final List<AttributeDto> attributes = Collections.singletonList(
                AttributeDto.builder()
                        .name(att1Name)
                        .attributeType(att1Type)
                        .build());

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .id(100L)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        final var categoryId = 1L;
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(categoryId);


        final var itemId = 1000L;
        String itemName = "Circle";
        final var attributeValue = String.valueOf(3.14D);

        var item = mock(Item.class);
        given(item.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue = mock(ItemAttributeValue.class);
        given(itemAttributeValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue));

        final var attributeValues = Collections.singletonMap(100L, attributeValue);
        ItemDto itemDto = ItemDto.builder()
                .id(itemId)
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        given(itemRepository.findById(itemId)).willReturn(Optional.of(item));
        given(itemMapper.mapAttachedEntity(itemDto, item, category)).willReturn(item);
        given(itemRepository.save(any(Item.class))).willReturn(item);

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        //when update
        itemService.update(itemDto);

        then(categoryRepository).should().findById(categoryId);
        then(itemRepository).should().findById(itemId);
        then(itemMapper).should().mapAttachedEntity(itemDto, item, category);
        then(itemRepository).should().save(item);
        then(attributeValidator).should(only()).validate(categoryDomainAttributes, attributeValues);
    }

    @Test
    void givenInvalidItem_whenUpdate_thenFailCategoryNotFoundThrown() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "factorX";

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .id(100L)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        final var categoryId = 1L;
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(categoryId);


        String itemName = "Circle";
        final var itemId = 1000L;
        final var attributeValue = String.valueOf(3.14D);

        var item = mock(Item.class);
        given(item.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue = mock(ItemAttributeValue.class);
        given(itemAttributeValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue));

        ItemDto itemDto = ItemDto.builder()
                .id(itemId)
                .categoryId(2L)
                .name(itemName)
                .attributeValues(new HashMap<>())
                .build();

        given(itemMapper.mapAttachedEntity(itemDto, item, category)).willReturn(item);
        given(itemRepository.findById(itemId)).willReturn(Optional.of(item));
        given(itemRepository.save(any(Item.class))).willReturn(item);

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        Assertions.assertThrows(CategoryNotFoundException.class, () -> itemService.update(itemDto));
    }

    @Test
    void givenInvalidItem_whenUpdate_thenFailItemNotFoundThrown() {
        final var categoryName = "sample_category";
        final var categoryDescription = "this is attributes sample category";
        final var att1Name = "factorX";

        Category category = mock(Category.class);
        final List<CategoryAttribute> categoryDomainAttributes = Collections.singletonList(
                CategoryAttribute.builder()
                        .name(att1Name)
                        .id(100L)
                        .attributeType(com.example.lab.domain.enumeration.AttributeType.DOUBLE)
                        .build()
        );
        final var categoryId = 1L;
        given(category.getName()).willReturn(categoryName);
        given(category.getDescription()).willReturn(categoryDescription);
        given(category.getAttributes()).willReturn(categoryDomainAttributes);
        given(category.getId()).willReturn(categoryId);


        final var itemId = 1000L;
        String itemName = "Circle";
        final var attributeValue = String.valueOf(3.14D);

        var item = mock(Item.class);
        given(item.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue = mock(ItemAttributeValue.class);
        given(itemAttributeValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue));

        ItemDto itemDto = ItemDto.builder()
                .id(itemId)
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(new HashMap<>())
                .build();

        given(itemMapper.mapAttachedEntity(itemDto, item, category)).willReturn(item);
        given(itemRepository.findById(1001L)).willReturn(Optional.of(item));
        given(itemRepository.save(any(Item.class))).willReturn(item);

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.update(itemDto));
    }
}