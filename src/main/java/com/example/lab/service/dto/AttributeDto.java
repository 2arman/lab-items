package com.example.lab.service.dto;

import com.example.lab.service.dto.base.IdentityBaseDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 4:53 PM
 **/
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("Attribute Dto for Category")
public class AttributeDto  extends IdentityBaseDto {

    @NotEmpty
    private String name;

    @NotNull
    private AttributeType attributeType;

}
