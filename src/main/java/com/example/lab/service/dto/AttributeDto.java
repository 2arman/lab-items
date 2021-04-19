package com.example.lab.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 4:53 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("Attribute Dto for Category")
public class AttributeDto {

    @NotEmpty
    private String name;

    @NotNull
    private AttributeTypeDto attributeTypeDto;

}
