package com.filot.filotshop.category.controller;

import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/categories/{category_id}")
    public List<CategoryDTO> showCategoryList(@PathVariable(value = "category_id") String name) {

        if (name.equals("parents")) return categoryService.findAllParentCategory();

        Category category = categoryService.findCategoryByName(name).
                orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        List<CategoryDTO> categoryDTOS = new ArrayList<>();
//
        for (Category child : category.getChild()) {
            CategoryDTO categoryDTO = CategoryDTO.createCategoryDTO(child);
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }
}
