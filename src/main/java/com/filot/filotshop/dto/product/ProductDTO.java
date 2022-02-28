package com.filot.filotshop.dto.product;


import com.filot.filotshop.entity.Product;
import lombok.*;

@AllArgsConstructor
@Data
public class ProductDTO {
    private Long id ;
    private String name;
    private int price;
    private String size;
    private String imageUrl;
    private int amount;

}
