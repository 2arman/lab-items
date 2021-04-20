package com.example.lab.domain;

import com.example.lab.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @Column(name = "item_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_item_category"))
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "item")
    private List<ItemAttributeValue> attributesValues;
}
