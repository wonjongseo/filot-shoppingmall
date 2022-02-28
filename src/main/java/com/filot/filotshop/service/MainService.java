package com.filot.filotshop.service;

import com.filot.filotshop.dto.category.CategoryDTO;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.dto.ProductsCategoriesDTO;
import com.filot.filotshop.repository.category.CategoryRepository;
import com.filot.filotshop.repository.product.ProductRepository;
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
        List<CategoryDTO> mainCategories = categoryRepository.findAllMainCategoriesToDTO();
        return new ProductsCategoriesDTO(mainJoinProducts, mainCategories);
    }


}
