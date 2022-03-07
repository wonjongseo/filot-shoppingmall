package com.filot.filotshop.commons.service;

import com.filot.filotshop.category.service.CategoryService;
import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.commons.entity.ProductsCategoriesDTO;
import com.filot.filotshop.category.repository.CategoryRepository;
import com.filot.filotshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public ProductsCategoriesDTO getProductsAndCategories(){
        List<ProductDTO> mainJoinProducts = productRepository.findAllProductsJsonType();
        List<CategoryDTO> mainCategories = categoryRepository.findAllParentCategory();
        return new ProductsCategoriesDTO(mainJoinProducts, mainCategories);
    }


}
