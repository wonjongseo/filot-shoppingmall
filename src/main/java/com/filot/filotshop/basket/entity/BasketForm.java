package com.filot.filotshop.basket.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class BasketForm {
    private  int count;
    private  String color;
    private  String size;
}
