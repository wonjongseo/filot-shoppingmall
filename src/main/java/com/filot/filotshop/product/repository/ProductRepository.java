package com.filot.filotshop.product.repository;

import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , ProductRepositoryCustom{

    Product findByName(String name);

    @Query("select new com.filot.filotshop.product.entity.ProductDTO (p.id,p.name,p.price,p.size, i.url, p.amount) from Product p inner  join p.images i")
    List<ProductDTO> findAllToDTO();




}
