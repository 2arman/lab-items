package com.example.lab.domain;

import com.example.lab.domain.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


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

    @Column(name = "category_description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category", fetch = FetchType.EAGER)
    private List<CategoryAttribute> attributes;
}
