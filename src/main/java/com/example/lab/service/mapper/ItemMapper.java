package com.example.lab.service.mapper;

import com.example.lab.domain.Category;
import com.example.lab.domain.Item;
import com.example.lab.domain.ItemAttributeValue;
import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.dto.ItemsDto;
import com.example.lab.service.exception.AttributeNotFoundException;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 6:12 PM
 **/
@Mapper(componentModel = "spring")
@Component
public interface ItemMapper {
    default ItemsDto mapList(Page<Item> items) {
        return ItemsDto.builder()
                .items(items.getContent()
                        .stream()
                        .map(this::mapDto)
                        .collect(Collectors.toList()))
                .build();
    }

    default Item mapAttachedEntity(ItemDto itemDto, Item item, Category category) {
        final var detachedItem = map(itemDto);
        item.setName(detachedItem.getName());
        item.setCategory(category);
        item.setAttributesValues(mapAttributeValues(itemDto.getAttributeValues(), item, category));
        return item;
    }

    default Item mapEntity(ItemDto itemDto, Category category) {
        var item = map(itemDto);
        item.setCategory(category);
        item.setAttributesValues(mapAttributeValues(itemDto.getAttributeValues(), item, category));
        return item;
    }

    default ItemDto mapDto(Item item) {
        ItemDto itemDto = map(item);
        itemDto.setCategoryId(item.getCategory().getId());
        itemDto.setAttributeValues(item.getAttributesValues()
                .stream()
                .collect(Collectors
                        .toMap(itemAttributeValue -> itemAttributeValue.getCategoryAttribute().getId(),
                                ItemAttributeValue::getValue)));
        return itemDto;
    }

    default List<ItemAttributeValue> mapAttributeValues(Map<Long, String> attributeValues, Item item, Category category) {
        return attributeValues.entrySet().stream().map((entry) -> {
            var founded = category.getAttributes()
                    .stream()
                    .filter(categoryAttribute -> categoryAttribute.getId().equals(entry.getKey()))
                    .findFirst()
                    .orElseThrow(() -> new AttributeNotFoundException("The attribute with this id is not found"));
            ItemAttributeValue itemAttributeValue = ItemAttributeValue.builder()
                    .categoryAttribute(founded)
                    .value(entry.getValue())
                    .item(item)
                    .build();
            if (item.getAttributesValues() != null) {
                for (ItemAttributeValue attributesValue : item.getAttributesValues()) {
                    if (attributesValue.getCategoryAttribute().getId().equals(entry.getKey())) {
                        attributesValue.setValue(itemAttributeValue.getValue());
                        itemAttributeValue = attributesValue;
                    }
                }
            }
            return itemAttributeValue;
        })
                .collect(Collectors.toList());
    }


    Item map(ItemDto itemDto);

    ItemDto map(Item item);
}
