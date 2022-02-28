package com.filot.filotshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FileImage {
    @Id
    @GeneratedValue
    Long id;

    private String uploadPath;
    private String uuid;
    private String fileName;


    @ManyToOne
    @JoinColumn(name = "FILE_IMAGE_PRODUCT_ID")
    @JsonIgnore
    private Product product;

    public void setProduct(Product product) {
        if (!product.getImageUrls().contains(this)) {
            product.getImageUrls().add(this);
        }
        this.product = product;
    }
}
