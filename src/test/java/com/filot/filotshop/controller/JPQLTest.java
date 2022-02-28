package com.filot.filotshop.controller;

import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.entity.Category;
import com.filot.filotshop.entity.EmailCheck;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.repository.EmailCheckRepository;
import com.filot.filotshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class JPQLTest {
    @Autowired
    EntityManager em;


    @Test
    public void 카테고리이름으로_제품_찾는다(){
        String jpql = "select new com.filot.filotshop.dto.product.ProductDTO (p.id,p.name,p.price,p.size, p.imageUrl) from Category c join c.products p where c.name = :name";

        List<ProductDTO> resultList = em.createQuery(jpql, ProductDTO.class).setParameter("name", "BEST").getResultList();
        for (ProductDTO product : resultList) {
            System.out.println("product = " + product);
        }
    }

    @Test
    public void 정렬해서_상품_보여준다() {
        String jpql = "select new com.filot.filotshop.dto.product.ProductDTO (p.id,p.name,p.price,p.size, p.imageUrl) from Category c join c.products p where c.name = :name ";

        jpql += "order by p.price desc";
//        jpql += "order by p.createdAt";


        List<ProductDTO> resultList = em.createQuery(jpql, ProductDTO.class)
                .setParameter("name", "COAT")
                .setFirstResult(0).
                setMaxResults(3).
                getResultList();
        for (ProductDTO productDTO : resultList) {
            System.out.println("productDTO = " + productDTO);
        }
        System.out.println();

    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailCheckRepository emailCheckRepository;
    @Test
    @Rollback(value = false)
    public void addEmailCode(){
        EmailCheck emailCheck = new EmailCheck();
        emailCheck.setEmail("visionwill");
        emailCheck.setCode("1234");
        emailCheckRepository.save(emailCheck);
    }
}
