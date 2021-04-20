package com.example.lab.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 11:33 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Categories Dto")
public class CategoriesDto{
    private List<CategoryDto> categories;
}
