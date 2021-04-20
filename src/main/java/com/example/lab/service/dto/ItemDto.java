package com.example.lab.service.dto;

import com.example.lab.service.dto.base.IdentityBaseDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 7:16 PM
 **/
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("Item")
public class ItemDto extends IdentityBaseDto {

    @NotNull(message = "category Id is mandatory")
    private Long categoryId;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "name must be a string")
    @NotEmpty(message = "name is mandatory field")
    private String name;

    Map<Long,String> attributeValues;
}
