package com.example.lab.api;

import com.example.lab.service.ItemService;
import com.example.lab.service.dto.ItemDto;
import com.example.lab.service.dto.ItemsDto;
import com.example.lab.service.dto.PageableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;


/**
 * @author Arman
 * Date: 4/17/21
 * Time: 7:45 PM
 **/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/items")
@Api("The Lab Items API")
public class ItemResource {
    private final ItemService itemService;

    @GetMapping
    @ApiOperation("Getting items of a category")
    public ResponseEntity<ItemsDto> getItems(@Valid @RequestParam Long categoryId,
                                             @Valid @RequestParam(defaultValue = "0") Integer page,
                                             @Valid @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(itemService.getAllByCategoryId(categoryId,
                PageableDto.builder()
                        .page(page)
                        .size(size)
                        .build()));
    }

    @PostMapping()
    @ApiOperation("Creating items in one specific category")
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto) {
        var responseItemDto = itemService.create(itemDto);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(responseItemDto.getId())
                        .toUri())
                .body(responseItemDto);
    }

    @PutMapping()
    @ApiOperation("Updating item")
    public ResponseEntity<Void> updateItem(@Valid @RequestBody ItemDto itemDto) {
        itemService.update(itemDto);
        return ResponseEntity
                .noContent()
                .build();

    }

    @GetMapping("/{id}")
    @ApiOperation("get item by Id")
    public ResponseEntity<ItemDto> getItem(@Valid @PathVariable("id") Long itemId) {
        return ResponseEntity.ok()
                .body(itemService.getById(itemId));

    }

}
