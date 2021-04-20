package com.example.lab.domain;

import com.example.lab.domain.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Positive;

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
@Table(name = "attribute_value")
public class ItemAttributeValue extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "fk_item_attribute_value"))
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_attribute_id", foreignKey = @ForeignKey(name = "fk_category_attribute_value"))
    private CategoryAttribute categoryAttribute;

    @Column(name = "value")
    private String value;
}
