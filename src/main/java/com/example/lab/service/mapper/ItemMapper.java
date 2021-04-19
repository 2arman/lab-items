package com.example.lab.service.mapper;

import com.example.lab.domain.Item;
import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.dto.ItemsDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

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
                .items(map(items.getContent()))
                .build();
    }

    Item map(ItemDto itemDto);

    ItemDto map(Item item);

    List<ItemDto> map(List<Item> items);
}
