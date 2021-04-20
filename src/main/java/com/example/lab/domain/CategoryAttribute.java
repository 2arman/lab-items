package com.example.lab.domain;

import com.example.lab.domain.base.BaseEntity;
import com.example.lab.domain.enumeration.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author Arman
 * Date: 4/18/21
 * Time: 2:34 PM
 **/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category_attribute")
public class CategoryAttribute extends BaseEntity {
    @Column(name = "attribute_name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeType attributeType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_attribute_category"))
    private Category category;

}
