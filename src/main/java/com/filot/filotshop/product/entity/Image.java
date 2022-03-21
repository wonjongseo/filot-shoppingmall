package com.filot.filotshop.product.entity;

import com.filot.filotshop.commons.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Image extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name =  "image_id")
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
