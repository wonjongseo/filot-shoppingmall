package com.filot.filotshop.category.repository;

import com.filot.filotshop.product.entity.ProductDTO;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<ProductDTO> findProductByCategoryName(String name, Integer page, String sort);
}
