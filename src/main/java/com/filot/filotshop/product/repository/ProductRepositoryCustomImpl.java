package com.filot.filotshop.product.repository;

import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.product.entity.ProductDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.filot.filotshop.product.service.ProductService.SHOW_PRODUCT_COUNT;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final EntityManager em;
    private final String SELECT_PRODUCT_DTO = "select p.id,p.name,p.price,p.size,p.imageUrl,p.amount";


    private List<ProductDTO> createProductDTO(List<Object[]> resultList) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Object[] objects : resultList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId((Long) objects[0]);
            productDTO.setName((String) objects[1]);
            productDTO.setPrice((Integer) objects[2]);
            productDTO.setSize((String) objects[3]);
            productDTO.setImageUrl((String)objects[4]);
            productDTO.setAmount((Integer) objects[5]);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }


    @Override
    public List<ProductDTO> findAllProductDTO(){
        List<Object[]> resultList = em.createQuery(SELECT_PRODUCT_DTO + " From Product p ").getResultList();
        return createProductDTO(resultList);
    }


    @Override
    public List findAllProductsJsonType() {
        return em.createQuery("select  new com.filot.filotshop.product.entity.ProductDTO (p.id, p.name, p.price, p.size,p. imageUrl ,p.amount) FROM Product p   ").getResultList();
    }

    @Override
    public List<ProductDTO> findProductByCategoryName(String name, Integer page, String sort) {
        String jpql = SELECT_PRODUCT_DTO + " From Category c join c.products p where c.name = :name ";


        switch (sort){
            case "name":
                jpql += "order by p.name";
                break;
            case  "price": // 값싼 순위
                jpql += "order by p.price";
                break;
            case "price_desc": // 비싼 순위
                jpql += "order by p.price desc";
                break;
            case "newest":
                jpql += "order by p.createdAt";
                break;
            default:
                throw new CustomException(ErrorCode.INVALID_REQUEST_SORT);
        }

        List<Object[]>  productObjs = em.createQuery(jpql)
                .setParameter("name",name)
                .setFirstResult( (page - 1) * SHOW_PRODUCT_COUNT)
                .setMaxResults( ((page - 1) * SHOW_PRODUCT_COUNT) + SHOW_PRODUCT_COUNT)
                .getResultList();

        return createProductDTO(productObjs);
    }






}
