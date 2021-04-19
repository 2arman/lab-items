package com.example.lab.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Arman
 * Date: 4/5/21
 * Time: 12:19 AM
 **/
@ResponseStatus(value = NOT_FOUND, reason = "Attribute not found the item.")
public class AttributeNotFoundException extends RuntimeException {
    public AttributeNotFoundException(String message) {
        super(message);
    }
}
