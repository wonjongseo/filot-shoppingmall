package com.filot.filotshop.dto.product;


import com.filot.filotshop.entity.Product;
import lombok.*;

@AllArgsConstructor
@Data
//@NoArgsConstructor
public class ProductDTO {
//    public static ProductDTO createProductDTO(Product product) {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setName(product.getName());
//        productDTO.setAmount(product.getAmount());
//        product.setImageUrls(productForm.getImageUrls());
//        productDTO.setImageUrl(product.getImageUrl());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setSize(product.getSize());
//        return productDTO;
//    }
    private Long id ;
    private String name;
    private int price;
    private String size;
    private String imageUrl;
    private int amount;


//    private String description;
}
