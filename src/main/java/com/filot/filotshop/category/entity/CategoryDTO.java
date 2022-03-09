package com.filot.filotshop.category.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

        for (Category category1 : category.getChild()) {
            categoryDTO.children.add(new CategoryDTO(category1.getId(), category1.getName()));
        }

        return categoryDTO;
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;
    private List<CategoryDTO> children = new ArrayList<>();

}
