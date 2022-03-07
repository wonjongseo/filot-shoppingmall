package com.filot.filotshop.controller;

import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.commons.entity.EmailCheckDTO;
import com.filot.filotshop.commons.repository.EmailCheckRepository;
import com.filot.filotshop.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class JPQLTest {
    @Autowired
    EntityManager em;

    @Test
    public void test1(){
        List<Object[]> resultList = em.createQuery("select p.id,p.name,p.price,p.size,p.imageUrl,p.amount From Product p ").getResultList();

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

        for (ProductDTO productDTO : productDTOS) {
            System.out.println("productDTO = " + productDTO);
        }
    }

}
