package com.filot.filotshop.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.filot.filotshop.dto.product.ProductForm;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.ObjectIdGenerators.*;

@Entity
@Data
@NoArgsConstructor
public class Product  extends  BaseEntity{

    public static Product createProduct(ProductForm productForm) {
        Product product = new Product();
        product.setName(productForm.getName());
        product.setAmount(productForm.getAmount());
//        product.setImageUrls(productForm.getImageUrls());
        product.setImageUrl(productForm.getImageUrl());
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());
        product.setSize(productForm.getSize());

        return product;
    }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    Long id;

    @Column(unique = true, length = 50)
    private String name;

    @Column
    private int price;

    // 없애도 될 듯 ?
    @Column(columnDefinition = "int(11) default 0")
    private int savePoint;

    @Column(columnDefinition = "int(11) default 1")
    private  int amount;
    //    @Lob

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<FileImage> imageUrls = new ArrayList<>();

    private String size;

    @Lob
    private  String description;

    private  String imageUrl;


//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "CATEGORY_PRODUCT_ID")
    private Category category;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PRODUCT_DISCOUNT_ID")
    private Discount discount;




    public void setCategory(Category category) {
        if (!category.getProducts().contains(this)) {
            category.getProducts().add(this);
        }
        this.category = category;
    }



}
