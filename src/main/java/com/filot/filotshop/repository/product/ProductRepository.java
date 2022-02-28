package com.filot.filotshop.repository.product;

import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> , ProductRepositoryCustom{

    Product findByName(String name);

    @Query("select new com.filot.filotshop.dto.product.ProductDTO (p.id,p.name,p.price,p.size, p.imageUrl, p.amount) from Product p")
    List<ProductDTO> findAllToDTO();




}
