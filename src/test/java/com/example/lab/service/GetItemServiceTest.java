package com.example.lab.service;

import com.example.lab.domain.Category;
import com.example.lab.domain.CategoryAttribute;
import com.example.lab.domain.Item;
import com.example.lab.domain.ItemAttributeValue;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.repository.ItemRepository;
import com.example.lab.service.dto.*;
import com.example.lab.service.exception.CategoryNotFoundException;
import com.example.lab.service.exception.ItemNotFoundException;
import com.example.lab.service.impl.ItemServiceImpl;
import com.example.lab.service.mapper.ItemMapper;
import com.example.lab.service.validation.AttributeValidator;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 11:46 PM
 **/
class GetItemServiceTest {

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
    void givenValidItemId_whenGetById_thenSuccessfullyReturn() {
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

        final var attributeValues = new HashMap<Long, String>();
        ItemDto itemDto = ItemDto.builder()
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        ItemDto itemResponseDto = ItemDto.builder()
                .id(itemId)
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        given(itemMapper.mapDto(any(Item.class))).willReturn(itemResponseDto);
        given(itemRepository.findById(itemId)).willReturn(Optional.of(item));

        final var actualItemResponseDto = itemService.getById(itemId);

        Assert.assertEquals(0, actualItemResponseDto.getAttributeValues().size());
        Assert.assertEquals(itemName, actualItemResponseDto.getName());
        Assert.assertEquals(Long.valueOf(categoryId), actualItemResponseDto.getCategoryId());
        Assert.assertNotNull(actualItemResponseDto.getId());
        Assert.assertEquals(Long.valueOf(itemId), actualItemResponseDto.getId());

        then(itemRepository).should().findById(itemId);
        then(itemMapper).should().mapDto(item);
    }

    @Test
    void givenInvalidItemId_whenGetById_thenFailItemNotFoundThrown() {
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

        final var attributeValues = new HashMap<Long, String>();
        ItemDto itemDto = ItemDto.builder()
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        ItemDto itemResponseDto = ItemDto.builder()
                .id(itemId)
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        given(itemMapper.map(any(Item.class))).willReturn(itemResponseDto);
        given(itemRepository.findById(1002L)).willReturn(Optional.of(item));

        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.getById(itemId));

    }

    @Test
    void givenValidCategoryId_whenGetAllByCategoryId_thenSuccessfullyReturn() {
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
        final var itemId2 = 1001L;
        String itemName2 = "RECTANGLE";
        final var attributeValue2 = String.valueOf(12.56D);

        var item1 = mock(Item.class);
        given(item1.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue = mock(ItemAttributeValue.class);
        given(itemAttributeValue.getValue()).willReturn(attributeValue);
        given(item1.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue));

        final var attributeValues = Collections.singletonMap(100L, attributeValue);

        var item2 = mock(Item.class);
        given(item1.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue2 = mock(ItemAttributeValue.class);
        given(itemAttributeValue2.getValue()).willReturn(attributeValue2);
        given(item2.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue2));

        final var attributeValues2 = Collections.singletonMap(100L, attributeValue2);

        ItemDto itemResponseDto1 = ItemDto.builder()
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        ItemDto itemResponseDto2 = ItemDto.builder()
                .id(itemId2)
                .categoryId(categoryId)
                .name(itemName2)
                .attributeValues(attributeValues2)
                .build();
        final var itemsDto = ItemsDto.builder()
                .items(List.of(itemResponseDto1, itemResponseDto2))
                .build();


        final var size = 10;
        final var page = 0;
        Page<Item> itemPages = new PageImpl<>(List.of(item1, item2));
        final var pageable = PageRequest.of(page, size);
        given(itemRepository.findAllByCategory_IdOrderByIdAsc(categoryId, pageable)).willReturn(itemPages);
        given(itemMapper.mapList(itemPages)).willReturn(itemsDto);
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        final var actualItemsResponseDto =
                itemService.getAllByCategoryId(categoryId, PageableDto.builder()
                        .size(size)
                        .page(page)
                        .build());

        Assert.assertEquals(2, actualItemsResponseDto.getItems().size());
        Assert.assertEquals(itemResponseDto1, actualItemsResponseDto.getItems().get(0));
        Assert.assertEquals(itemResponseDto2, actualItemsResponseDto.getItems().get(1));

        then(categoryRepository).should(only()).findById(categoryId);
        then(itemRepository).should(only()).findAllByCategory_IdOrderByIdAsc(categoryId, pageable);
        then(itemMapper).should().mapList(itemPages);
    }

    @Test
    void givenInvalidCategoryId_whenGetAllByCategoryId_thenFailCategoryNotFoundThrown() {
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
        final var itemId2 = 1001L;
        String itemName2 = "RECTANGLE";
        final var attributeValue2 = String.valueOf(12.56D);

        var item1 = mock(Item.class);
        given(item1.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue = mock(ItemAttributeValue.class);
        given(itemAttributeValue.getValue()).willReturn(attributeValue);
        given(item1.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue));

        final var attributeValues = Collections.singletonMap(100L, attributeValue);

        var item2 = mock(Item.class);
        given(item1.getName()).willReturn(itemName);
        ItemAttributeValue itemAttributeValue2 = mock(ItemAttributeValue.class);
        given(itemAttributeValue2.getValue()).willReturn(attributeValue2);
        given(item2.getAttributesValues()).willReturn(Collections.singletonList(itemAttributeValue2));

        final var attributeValues2 = Collections.singletonMap(100L, attributeValue2);

        ItemDto itemResponseDto1 = ItemDto.builder()
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(attributeValues)
                .build();

        ItemDto itemResponseDto2 = ItemDto.builder()
                .id(itemId2)
                .categoryId(categoryId)
                .name(itemName2)
                .attributeValues(attributeValues2)
                .build();
        final var itemsDto = ItemsDto.builder()
                .items(List.of(itemResponseDto1, itemResponseDto2))
                .build();


        final var size = 10;
        final var page = 0;
        Page<Item> itemPages = new PageImpl<>(List.of(item1, item2));
        given(itemRepository.findAllByCategory_IdOrderByIdAsc(categoryId, PageRequest.of(page, size))).willReturn(itemPages);
        given(itemMapper.mapList(itemPages)).willReturn(itemsDto);
        given(categoryRepository.findById(2L)).willReturn(Optional.of(category));

        Assertions.assertThrows(CategoryNotFoundException.class, () -> itemService.getAllByCategoryId(categoryId, PageableDto.builder()
                .size(size)
                .page(page)
                .build()));

    }
}