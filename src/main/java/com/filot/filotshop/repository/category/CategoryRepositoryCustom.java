package com.filot.filotshop.repository.category;

import com.filot.filotshop.dto.category.CategoryDTO;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.entity.Product;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<ProductDTO> findProductByCategoryName(String name, Integer page, String sort);
}
