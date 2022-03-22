package com.filot.filotshop.product.entity;


import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductDTO {

    public static ProductDTO createProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSize(product.getSize());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setId(product.getId());
        productDTO.setAmount(product.getAmount());
        return productDTO;
    }

    private Long id ;
    private String name;
    private int price;
    private String size;
    private String imageUrl;
    private int amount;

}
