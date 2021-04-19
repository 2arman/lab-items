package com.example.lab.domain;

import com.example.lab.domain.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author Arman
 * Date: 4/18/21
 * Time: 2:34 PM
 **/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @Column(name = "category_name")
    private String name;
}
