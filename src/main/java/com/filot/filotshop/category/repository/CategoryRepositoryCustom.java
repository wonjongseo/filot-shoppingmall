package com.filot.filotshop.category.repository;

import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.product.entity.ProductDTO;

import java.util.List;

public interface CategoryRepositoryCustom {


    List<CategoryDTO> findAllParentCategory();
}
