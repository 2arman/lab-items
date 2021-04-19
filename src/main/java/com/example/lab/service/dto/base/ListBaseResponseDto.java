package com.example.lab.service.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 8:47 AM
 **/
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class ListBaseResponseDto {
    private long total;
}
