package com.filot.filotshop.admin.controller;


import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.category.entity.CategoryForm;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> postCategory(@RequestBody CategoryForm form) {
        Category category = categoryService.addCategory(form);
        CategoryDTO categoryDTO = CategoryDTO.createCategoryDTO(category);

        return ResponseEntity.status(200).body(categoryDTO);
    }


}
