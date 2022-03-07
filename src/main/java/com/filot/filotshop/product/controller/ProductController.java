package com.filot.filotshop.product.controller;

import com.filot.filotshop.product.entity.DetailProductDTO;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.category.service.CategoryService;
import com.filot.filotshop.product.repository.ProductRepository;
import com.filot.filotshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController

public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;


    @GetMapping(value = "/products/{category_name}")
    public List<ProductDTO> showProductByCategoryName(
            @PathVariable(value = "category_name") String name,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "sort", required = false) String sort) {

        if (page == null && sort == null) {
            // 따로 정렬 쿼리 없으면
            if (name.equals("main")) {
                // 모든 상품 조회
                //findProductByCategoryName
                return productService.findAllProducts();
            } else {
                // 카테고리 이름에 맞는 상품 조회
                return categoryService.findProductByName(name);
            }
        } else {
            return productRepository.findProductByCategoryName(name, page, sort);
        }
    }
    @GetMapping(value = "/product/{product_id}")
    public DetailProductDTO showProductById(@PathVariable(name = "product_id") Long id) {
        Product product = productService.findProductById(id);
        return DetailProductDTO.createDetailProductDTO(product);
    }

}

//43
//33

// -> 76