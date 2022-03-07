package com.filot.filotshop.product.repository;

import com.filot.filotshop.product.entity.ProductDTO;

import java.util.List;

public interface ProductRepositoryCustom {
     List<ProductDTO> findAllProductsJsonType();
     List<ProductDTO> findProductByCategoryName(String name, Integer page, String sort);
     List<ProductDTO> findAllProductDTO();

}
