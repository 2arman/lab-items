package com.example.lab.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 9:57 AM
 **/
@Data
@Builder
@AllArgsConstructor
public class PageableDto {
    private final int page;
    private final int size;
}
