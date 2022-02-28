package com.filot.filotshop.repository.category;

import com.filot.filotshop.dto.category.CategoryDTO;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.List;

import static com.filot.filotshop.service.ProductService.SHOW_PRODUCT_COUNT;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{

    private final EntityManager em;




    @Override
    public List<ProductDTO> findProductByCategoryName(String name, Integer page, String sort) {
        System.out.println("it it called");
        String jpql = "select new com.filot.filotshop.dto.product.ProductDTO (p.id,p.name,p.price,p.size, p.imageUrl) from Category c join c.products p where c.name = :name ";

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
//        else if (sort.equals("평점높은순")) {
//
//        }

       return em.createQuery(jpql, ProductDTO.class)
                .setParameter("name",name)
                .setFirstResult( (page - 1) * SHOW_PRODUCT_COUNT)
                .setMaxResults( ((page - 1) * SHOW_PRODUCT_COUNT) + SHOW_PRODUCT_COUNT)
                .getResultList();



    }
}
