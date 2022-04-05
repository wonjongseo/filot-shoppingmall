package com.filot.filotshop.product.entity;

import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.commons.entity.BaseEntity;
import com.filot.filotshop.commons.entity.Discount;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product  extends BaseEntity {

    public static Product createProduct(ProductForm productForm, String url) {
        Product product = new Product();
        product.setName(productForm.getName());
        product.setAmount(productForm.getAmount());
        product.setImageUrl(url);
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());
        product.setSize(productForm.getSize());
        product.setColor(productForm.getColor());
        return product;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    Long id;

    @Column(unique = true, length = 50)
    private String name;

    @Column
    private int price;

    @Column(columnDefinition = "int(11) default 0")
    private int savePoint;

    @Column(columnDefinition = "int(11) default 1")
    private  int amount;

    //    @Lob
    @OneToMany(mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private String size;

    @Lob
    private  String description;
    private  String imageUrl;
    private String color;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_PRODUCT_ID")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Basket> baskets = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PRODUCT_DISCOUNT_ID")
    private Discount discount;

    public void changeAmount(int amount){
        int changedAmount = this.getAmount() + amount;
        if(changedAmount < 0 )
            throw new CustomException(ErrorCode.INVALID_AMOUNT);
        this.amount = changedAmount;
    }

    public void setCategory(Category category) {
        if (!category.getProducts().contains(this)) {
            category.getProducts().add(this);
        }
        this.category = category;
    }

}
