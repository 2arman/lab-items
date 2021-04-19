package com.example.lab.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 10:36 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDto {
    private List<ItemDto> items;
}
