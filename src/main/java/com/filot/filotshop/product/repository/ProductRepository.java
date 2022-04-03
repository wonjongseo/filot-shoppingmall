package com.filot.filotshop.product.repository;

import com.filot.filotshop.product.entity.Image;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , ProductRepositoryCustom{

    Product findByName(String name);

        /*
    @Query("select m from Member m where m.username = ?1")
    Member findByusername(String username);
     */

    @Query("select i from Product  p join p.images i where i.name = :name and p.id = :id")
    Image findImageByProductIdAndImageName(String name, Long id);
}
