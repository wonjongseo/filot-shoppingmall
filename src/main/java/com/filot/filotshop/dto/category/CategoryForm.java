package com.filot.filotshop.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CategoryForm {
    private  String name;
    private String parentName;
}
