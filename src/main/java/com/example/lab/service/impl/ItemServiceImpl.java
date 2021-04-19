package com.example.lab.service.impl;

import com.example.lab.domain.Category;
import com.example.lab.domain.Item;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.repository.ItemRepository;
import com.example.lab.service.CategoryService;
import com.example.lab.service.ItemService;
import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.dto.ItemsDto;
import com.example.lab.service.dto.PageableDto;
import com.example.lab.service.exception.CategoryNotFoundException;
import com.example.lab.service.exception.ItemNotFoundException;
import com.example.lab.service.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 6:12 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ItemsDto getAllByCategoryId(Long categoryId, PageableDto pageableDto) {
        final var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("The category not found!"));
        final var items = itemRepository.findAllByCategory_IdOrderByIdAsc(categoryId, PageRequest.of(pageableDto.getPage(), pageableDto.getSize()));
        return itemMapper.mapList(items);
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        return itemMapper.map(itemRepository.save(itemMapper.map(itemDto)));
    }

    @Override
    public ItemDto getById(Long itemId) {
        return itemMapper.map(getItemById(itemId));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(ItemDto itemDto) {
        final var category = categoryRepository.findById(itemDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("The category not found!"));
        var item  = getItemById(itemDto.getId());
        item = itemMapper.map(itemDto);
        itemRepository.save(item);
    }

    private Item getItemById(Long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item could not found"));
    }
}
