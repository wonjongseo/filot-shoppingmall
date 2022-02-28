package com.filot.filotshop.dto.product;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class ProductForm {
    private String name;
    private int price;
    private  int amount;
    private String size;
    private  String description;
    private String color;
    private String categoryName;


}
