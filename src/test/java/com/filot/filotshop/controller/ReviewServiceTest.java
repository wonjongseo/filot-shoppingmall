package com.filot.filotshop.controller;


import com.filot.filotshop.review.entity.ReviewDTO;
import com.filot.filotshop.review.entity.ReviewForm;
import com.filot.filotshop.product.entity.ProductForm;
import com.filot.filotshop.user.entity.JoinForm;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.review.entity.Review;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.review.controller.ReviewRepository;
import com.filot.filotshop.product.repository.ProductRepository;
import com.filot.filotshop.product.service.ProductService;
import com.filot.filotshop.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.yml"
)
public class ReviewServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    JoinForm userForm;
    ReviewForm reviewForm;
    ProductForm productForm;

    @BeforeEach
    public void setUp(){

        userForm = UserServiceTest.userForm;
        productForm = ProductServiceTest.productForm;
        reviewForm = new ReviewForm();
        reviewForm.setContent("Hello~~ Hello~~ Hello~~ Hello~~ ");
        reviewForm.setTitle("Hello!!");
        reviewForm.setRate(5);

    }

    @Test
    public void 리뷰남긴다() {
        User user = userService.join(userForm);
        System.out.println("productForm = " + productForm);
        Product product = productService.addProduct(productForm);

        Review review = new Review();
        review.setContent(reviewForm.getContent());
        review.setTitle(reviewForm.getTitle());
        review.setRate(reviewForm.getRate());

        review.setUser(user);
        review.setProduct(product);

        assertThat(user.getReviews().size()).isEqualTo(1);
        assertThat(product.getReviews().size()).isEqualTo(1);

        assertThat(review.getUser()).isNotNull();
        assertThat(review.getProduct()).isNotNull();
    }

    @Autowired
    EntityManager em;
    
    
    @Test
    public void 유저가_작성한_특정상품_리뷰를_본다(){
        User user = userService.findUserByEmail(userForm.getEmail());
        Product product = productRepository.findByName(productForm.getName());

        String jpql = "select r from Review r inner join r.user u where u.email = :email and r.product.id = :productId";
        List<Review> resultList = em.createQuery(jpql, Review.class).setParameter("email", user.getEmail()).setParameter("productId", product.getId()).getResultList();

        for (Review review : resultList) {
            System.out.println("review.getRate() = " + review.getRate());
            System.out.println("review.getContent() = " + review.getContent());
            System.out.println("review.getTitle() = " + review.getTitle());
        }
    }

    @Autowired
    ReviewRepository reviewRepository;
    @Test
    public void 유저가_리뷰_수정한다(){
        Review review = reviewRepository.getById(2L);
        System.out.println("review.getContent() = " + review.getContent());
        review.setContent("NEW CONTENT");

        String jpql = "select r from Review r inner join r.user u where u.email = :email and r.product.id = :productId";
        List<Review> resultList = em.createQuery(jpql, Review.class).setParameter("email", "adminA2").setParameter("productId", 82L).getResultList();

        for (Review review1 : resultList) {
            System.out.println("review1.getContent() = " + review1.getContent());
        }

    }
    @Test
    public void 상품_리뷰_연관관계_조인(){
        Product product1 = productRepository.findByName(productForm.getName());
        String jpql = "select p from Product p join fetch p.reviews where p =  :p";

        List<Product> resultList = em.createQuery(jpql, Product.class)
                .setParameter("p" ,product1)
                .getResultList();

        for (Product product : resultList) {
            System.out.println("product.getName() = " + product.getName());

            for (Review review : product.getReviews()) {
                System.out.println("review.getRate() = " + review.getRate());
                System.out.println("review.getContent() = " + review.getContent());
                System.out.println("review.getTitle() = " + review.getTitle());
            }
        }
    }

    @Test
    public void 상품_리뷰_DTO로_반환(){
        String jpql = "select NEW com.filot.filotshop.review.entity.ReviewDTO(r.id, r.title,r.rate,r.createAt,u.name) From Product p inner join p.reviews r inner join r.user u where p.id = :id";
        List<ReviewDTO> productId = em.createQuery(jpql, ReviewDTO.class).setParameter("id", 82L).getResultList();
        for (ReviewDTO reviewDTO : productId) {
            System.out.println("reviewDTO = " + reviewDTO);
        }
    }
}
