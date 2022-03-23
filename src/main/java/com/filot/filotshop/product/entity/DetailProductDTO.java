package com.filot.filotshop.product.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DetailProductDTO {

    public static DetailProductDTO createDetailProductDTO(Product product) {
        DetailProductDTO detailProductDTO = new DetailProductDTO();
        detailProductDTO.setId(product.getId());
        detailProductDTO.setName(product.getName());
        detailProductDTO.setPrice(product.getPrice());
        detailProductDTO.setMainImage(product.getImageUrl());
        detailProductDTO.images = new ArrayList<>();

        List<Image> images = product.getImages();
        for (Image image : images) {
            detailProductDTO.images.add(image.getUrl());
        }

        detailProductDTO.setDescription(product.getDescription());
        detailProductDTO.colors = new ArrayList<>();

        String colors = product.getColor();
        String[] splitedColor = colors.split(",");
        for (String color : splitedColor) {
            detailProductDTO.colors.add(color);
        }

        detailProductDTO.setSize(product.getSize());
        detailProductDTO.setAmount(product.getAmount());
        return detailProductDTO;
    }

    private Long id ;
    private String name;
    private int price;
    private String size;

    private List<String> images;
    private String mainImage;
    private String description;
    private List<String> colors;

    private int amount;

}
