package com.example.lab.service;

import com.example.lab.domain.Category;
import com.example.lab.domain.CategoryAttribute;
import com.example.lab.domain.Item;
import com.example.lab.domain.ItemAttributesValue;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.repository.ItemRepository;
import com.example.lab.service.dto.AttributeDto;
import com.example.lab.service.dto.AttributeType;
import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.exception.AttributeNotFoundException;
import com.example.lab.service.exception.CategoryNotFoundException;
import com.example.lab.service.impl.ItemServiceImpl;
import com.example.lab.service.mapper.ItemMapper;
import com.example.lab.service.validation.AttributeValidator;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
class CreateItemServiceTest {

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
    void givenValidItem_whenCreate_thenSuccessfullySaved() {
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
        ItemAttributesValue itemAttributesValue = mock(ItemAttributesValue.class);
        given(itemAttributesValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributesValue));

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

        given(itemMapper.map(any(ItemDto.class))).willReturn(item);
        given(itemRepository.save(any(Item.class))).willReturn(item);
        given(itemMapper.map(any(Item.class))).willReturn(itemResponseDto);

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        final var actualItemResponseDto = itemService.create(itemDto);

        Assert.assertEquals(0, actualItemResponseDto.getAttributeValues().size());
        Assert.assertEquals(itemName, actualItemResponseDto.getName());
        Assert.assertEquals(Long.valueOf(categoryId), actualItemResponseDto.getCategoryId());
        Assert.assertNotNull(actualItemResponseDto.getId());
        Assert.assertEquals(Long.valueOf(itemId), actualItemResponseDto.getId());

        then(categoryRepository).should(only()).findById(categoryId);
        then(itemMapper).should().map(itemDto);
        then(itemRepository).should(only()).save(item);
        then(itemMapper).should().map(item);
        then(attributeValidator).should(only()).validate(categoryDomainAttributes, attributeValues);

    }

    @Test
    void givenValidItemWithOneAttributeValue_whenCreate_thenSuccessfullySaved() {
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
        ItemAttributesValue itemAttributesValue = mock(ItemAttributesValue.class);
        given(itemAttributesValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributesValue));

        final var attributeValues = Collections.singletonMap(100L, attributeValue);
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

        given(itemMapper.map(any(ItemDto.class))).willReturn(item);
        given(itemRepository.save(any(Item.class))).willReturn(item);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(itemMapper.map(any(Item.class))).willReturn(itemResponseDto);


        final var actualItemResponseDto = itemService.create(itemDto);

        Assert.assertEquals(1, actualItemResponseDto.getAttributeValues().size());
        Assert.assertEquals(attributeValues, actualItemResponseDto.getAttributeValues());
        Assert.assertEquals(itemName, actualItemResponseDto.getName());
        Assert.assertEquals(Long.valueOf(categoryId), actualItemResponseDto.getCategoryId());
        Assert.assertNotNull(actualItemResponseDto.getId());
        Assert.assertEquals(Long.valueOf(itemId), actualItemResponseDto.getId());

        then(categoryRepository).should(only()).findById(categoryId);
        then(itemMapper).should().map(itemDto);
        then(itemRepository).should(only()).save(item);
        then(itemMapper).should().map(item);
        then(attributeValidator).should(only()).validate(categoryDomainAttributes, attributeValues);
    }

    @Test
    void givenInvalidItemCategory_whenCreate_thenFailCategoryNotFoundThrown() {
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
        final var attributeValue = String.valueOf(3.14D);

        var item = mock(Item.class);
        given(item.getName()).willReturn(itemName);
        ItemAttributesValue itemAttributesValue = mock(ItemAttributesValue.class);
        given(itemAttributesValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributesValue));

        ItemDto itemDto = ItemDto.builder()
                .categoryId(2L)
                .name(itemName)
                .attributeValues(new HashMap<>())
                .build();

        ItemDto itemResponseDto = ItemDto.builder()
                .id(1000L)
                .categoryId(categoryId)
                .name(itemName)
                .attributeValues(new HashMap<>())
                .build();

        given(itemMapper.map(any(ItemDto.class))).willReturn(item);
        given(itemRepository.save(any(Item.class))).willReturn(item);
        given(itemMapper.map(any(Item.class))).willReturn(itemResponseDto);

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        Assertions.assertThrows(CategoryNotFoundException.class, () -> itemService.create(itemDto));
    }

    @Test
    void givenInvalidItemAttribute_whenCreate_thenFailAttributeNotFoundThrown() {
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
        ItemAttributesValue itemAttributesValue = mock(ItemAttributesValue.class);
        given(itemAttributesValue.getValue()).willReturn(attributeValue);
        given(item.getAttributesValues()).willReturn(Collections.singletonList(itemAttributesValue));

        final var attributeValues = Collections.singletonMap(100L, attributeValue);
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

        given(itemMapper.map(any(ItemDto.class))).willReturn(item);
        given(itemRepository.save(any(Item.class))).willReturn(item);
        given(itemMapper.map(any(Item.class))).willReturn(itemResponseDto);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        willThrow(new AttributeNotFoundException("attribute not found"))
                .given(attributeValidator).validate(categoryDomainAttributes, attributeValues);
        Assertions.assertThrows(AttributeNotFoundException.class, () -> itemService.create(itemDto));
    }
}