package com.filot.filotshop.basket.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketDTO {
    private Long id;
    private  String productName;
    private  int productPrice;
    private  int totalPrice;
    private  String productOption;
    private  int selectedCount;
    private String productUrl;

    public static BasketDTO createBasketDTO(Basket basket) {
        return BasketDTO
                .builder()
                .id(basket.getId())
                .productName(basket.getProduct().getName())
                .productPrice(basket.getProduct().getPrice())
                .productOption(basket.getProductColor() + "/" + basket.getProductSize())
                .selectedCount(basket.getProductCount())
                .totalPrice(basket.getTotalPrice())
                .productUrl(basket.getProduct().getImageUrl())
                .build();
    }

}
