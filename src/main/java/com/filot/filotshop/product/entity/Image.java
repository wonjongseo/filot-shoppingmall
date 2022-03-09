package com.filot.filotshop.product.entity;

import com.filot.filotshop.commons.entity.BaseEntity;
import com.filot.filotshop.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Image extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_PRODUCT_ID")
    private Product product;

    public void setProduct(Product product) {
        if (!product.getImages().contains(this)) {
            product.getImages().add(this);
        }
        this.product = product;
    }

}
