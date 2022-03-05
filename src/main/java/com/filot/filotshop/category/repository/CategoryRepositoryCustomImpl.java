package com.filot.filotshop.category.repository;

import com.filot.filotshop.product.entity.ProductDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.filot.filotshop.product.service.ProductService.SHOW_PRODUCT_COUNT;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{

    private final EntityManager em;




    @Override
    public List<ProductDTO> findProductByCategoryName(String name, Integer page, String sort) {
        System.out.println("it it called");
        String jpql = "select new com.filot.filotshop.product.entity.ProductDTO (p.id,p.name,p.price,p.size, p.imageUrl,p.amount) from Category c join c.products p where c.name = :name ";

        if (sort.equals("name")) {
            jpql += "order by p.name";
        }else if(sort.equals("price")) {
            jpql += "order by p.price";
        }
        else if(sort.equals("price_desc")){
            jpql += "order by p.price desc";
        } else if (sort.equals("newest")) {
            jpql += "order by p.createdAt";
        }

       return em.createQuery(jpql, ProductDTO.class)
                .setParameter("name",name)
                .setFirstResult( (page - 1) * SHOW_PRODUCT_COUNT)
                .setMaxResults( ((page - 1) * SHOW_PRODUCT_COUNT) + SHOW_PRODUCT_COUNT)
                .getResultList();



    }
}
