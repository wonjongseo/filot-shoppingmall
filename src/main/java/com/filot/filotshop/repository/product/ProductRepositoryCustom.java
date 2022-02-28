package com.filot.filotshop.repository.product;

import com.filot.filotshop.dto.product.ProductDTO;

import java.util.List;

public interface ProductRepositoryCustom {
     List<ProductDTO> findAllProductsJsonType();
     List<ProductDTO> findProductsSortedByName(String categoryName, Integer page, Integer size);
     List<ProductDTO> findProductsSortedByPriceASC(String categoryName, Integer page, Integer size);
     List<ProductDTO> findProductsSortedByPriceDESC(String categoryName, Integer page, Integer size);
     List<ProductDTO> findProductsSortedByNewest(String categoryName, Integer page, Integer size);
}
