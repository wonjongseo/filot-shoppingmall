package com.filot.filotshop.category.repository;

import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom{

     Optional<Category> findByName(String name);

     List<Category> findChildrenByParentId(Long parentId);

     @Query("select new com.filot.filotshop.product.entity.ProductDTO (p.id,p.name,p.price,p.size, p.imageUrl,p.amount ) from Category c join c.products p where c.name = :name")
     List<ProductDTO> findProductByCategoryName(@Param("name") String name);

//     @Query("SELECT new com.filot.filotshop.category.entity.CategoryDTO(c.id,c.name) FROM Category c where c.parent is null")
//     List<CategoryDTO> findAllMainCategoriesToDTO();

}
