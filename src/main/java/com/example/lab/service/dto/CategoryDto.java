package com.example.lab.service.dto;

import com.example.lab.service.dto.base.IdentityBaseDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 4:53 PM
 **/
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("Category Dto for Lab")
public class CategoryDto extends IdentityBaseDto {


    @NotEmpty(message = "name is required.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "name must be a string")
    private String name;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "description must be a string")
    private String description;

    @Size(min = 1)
    @NotNull
    private List<AttributeDto> attributes;

}
