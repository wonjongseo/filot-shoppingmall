package com.filot.filotshop.dto;

import com.filot.filotshop.dto.category.CategoryDTO;
import com.filot.filotshop.dto.product.ProductDTO;
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
