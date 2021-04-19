package com.example.lab.api;

import com.example.lab.service.CategoryService;
import com.example.lab.service.dto.CategoriesDto;
import com.example.lab.service.dto.CategoryDto;
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
@RequestMapping(value = "/api/v1/categories", produces = "application/json")
@Api("The Lab Categories API")
public class CategoryResource {
    private CategoryService categoryService;

    @GetMapping
    @ApiOperation("Getting All Categories by pagination")
    public ResponseEntity<CategoriesDto> getCategories(@RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(categoryService.getAll(
                PageableDto.builder()
                .page(page)
                .size(size)
                .build()));
    }

    @PostMapping
    @ApiOperation("Creating categories with attribute definitions")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
         var responseCategoryDto  = categoryService.create(categoryDto);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(categoryDto.getId())
                        .toUri())
                .body(responseCategoryDto);
    }

    @GetMapping("/{id}")
    @ApiOperation("Getting category by Id")
    public ResponseEntity<CategoryDto> getCategoryById(@Valid @PathVariable("id") Long categoryId) {
        return ResponseEntity.ok()
                .body(categoryService.getById(categoryId));

    }
}
