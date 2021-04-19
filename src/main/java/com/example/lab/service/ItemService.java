package com.example.lab.service;

import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.dto.ItemsDto;
import com.example.lab.service.dto.PageableDto;
import org.springframework.http.ResponseEntity;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 9:51 AM
 **/
public interface ItemService {
    ItemsDto getAllByCategoryId(Long categoryId, PageableDto pageableDto);

    ItemDto create(ItemDto itemDto);

    ItemDto getById(Long itemId);

    void update(ItemDto itemDto);
}
