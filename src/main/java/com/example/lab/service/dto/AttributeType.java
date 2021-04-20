package com.example.lab.service.dto;

import io.swagger.annotations.ApiModel;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 7:14 PM
 **/
@ApiModel("Attribute Type")
public enum AttributeType {
    TEXT,
    NUMBER,
    DOUBLE,
    BINARY,
}
