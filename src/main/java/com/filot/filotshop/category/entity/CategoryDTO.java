package com.filot.filotshop.category.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@Setter
@NoArgsConstructor
public class CategoryDTO {

    public static CategoryDTO createCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName(category.getName());
        categoryDTO.setId(category.getId());
//        categoryDTO.children = new ArrayList<>();
//        for (Category category1 : category.getChild()) {
//            categoryDTO.children.add(new CategoryDTO(category1.getId(), category1.getName()));
//        }

        return categoryDTO;
    }



    private Long id;
    private String name;
}
