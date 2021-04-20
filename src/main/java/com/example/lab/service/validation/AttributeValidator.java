package com.example.lab.service.validation;

import com.example.lab.domain.CategoryAttribute;
import com.example.lab.service.exception.AttributeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Arman
 * Date: 4/19/21
 * Time: 11:59 PM
 **/
@Component
public class AttributeValidator {

    public void validate(List<CategoryAttribute> attributes, Map<Long, String> attributeValues) {
        attributeValues.forEach(
                (attributeId, s) -> attributes.stream().filter(categoryAttribute -> categoryAttribute.getId().equals(attributeId))
                        .findAny()
                        .orElseThrow(() -> new AttributeNotFoundException("The attribute with this id is not found"))
        );
    }
}
