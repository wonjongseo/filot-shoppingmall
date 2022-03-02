package com.filot.filotshop.category.entity;

import com.filot.filotshop.commons.entity.BaseEntity;
import com.filot.filotshop.product.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Category extends BaseEntity {

    public Category(String name, List<Product> products, Category parent) {
        this.name = name;
        this.products = products;
        this.parent = parent;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;


    @Column(unique = true, length = 10)
    private String name;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "CATEGORY_PRODUCT",
//            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
//            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
//    )
//    private List<Product> products = new ArrayList<>();


    @OneToMany(mappedBy = "category", fetch =  FetchType.LAZY)
    private List<Product> products = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent" ,fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category category) {
        if(!child.contains(category))
            child.add(category);
        category.setParent(this);
    }





}
