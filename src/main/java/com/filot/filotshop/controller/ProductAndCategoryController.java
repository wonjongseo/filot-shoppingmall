package com.filot.filotshop.controller;


import com.filot.filotshop.dto.category.CategoryDTO;
import com.filot.filotshop.dto.product.DetailProductDTO;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.entity.Category;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.product.ProductRepository;
import com.filot.filotshop.service.CategoryService;
import com.filot.filotshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ProductAndCategoryController {


    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    // ok
    @GetMapping(value = "/product-list/{category_name}")
    public List<ProductDTO> showProductByCategoryName(
            @PathVariable(value = "category_name") String name,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "sort", required = false) String sort) {

        if (page == null && sort == null) {
            // 따로 정렬 쿼리 없으면
            if (name.equals("main")) {
                // 모든 상품 조회
                return productService.findAllProducts();
            } else {
                // 카테고리 이름에 맞는 상품 조회
                return categoryService.findProductByName(name);
            }
        } else {
            return categoryService.findProductByName(name, page, sort);
        }
    }


    // ok
    @GetMapping(value = "/category-list/{category_id}")
    public List<CategoryDTO> showCategoryList(@PathVariable(value = "category_id") String name) {

        if (name.equals("main")) return categoryService.findAllMainCategories();

        Category category = categoryService.findCategoryByName(name).
                orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for (Category child : category.getChild()) {
            CategoryDTO categoryDTO = new CategoryDTO(child.getId(), child.getName());
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    // ok
    @GetMapping(value = "/products/{product_id}")
    public DetailProductDTO showProductById(@PathVariable(name = "product_id") Long id) {
        System.out.println("id = " + id);
        Product product = productService.findProductById(id);
        return DetailProductDTO.createProductDTO(product);
    }



}
