package com.filot.filotshop.commons.entity;

import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.product.entity.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@AllArgsConstructor
@Getter
@ToString
public class ProductsCategoriesDTO {
    private List<ProductDTO> products;
    private List<CategoryDTO> categories;
}
