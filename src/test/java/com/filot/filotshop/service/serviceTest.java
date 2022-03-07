package com.filot.filotshop.service;

import com.filot.filotshop.basket.service.BasketService;
import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.basket.entity.BasketDTO;
import com.filot.filotshop.basket.entity.BasketForm;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.ProductForm;
import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.product.repository.ProductRepository;
import com.filot.filotshop.product.service.ProductService;
import com.filot.filotshop.user.repository.UserRepository;
import com.filot.filotshop.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional

class serviceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;
    ProductForm form;
    String productName = "빨강 상의2";
//    @BeforeEach
//    public void setUp(){
//
//    }

    @Test
    public void showAllProduct(){
        List<Product> allProducts = productService.findAllProductsForAdmin();

        for (Product allProduct : allProducts) {
            System.out.println(allProduct.getName());
        }
    }

    @Test
    public void 상품등록한다(){
        form = ProductForm.
                builder()

                .amount(1000)
                .name("빨간 상의")
                .description("빨간 상의 입니다")
                .price(10000)
                .categoryName("BEST")

                .build();
        Product product = productService.addProduct(form);
        System.out.println("product = " + product);
    }

    @Autowired
    BasketService basketService;
    
    @Test
    public void 상품장바구니에_넣는다(){
        Product product = productService.findProductById(35L);
        int amount = product.getAmount();
        int selectedCount  =50;
        System.out.println("amount = " + amount);

        BasketForm basketForm = 
                BasketForm.builder()
                        .color("red")
                        .count(selectedCount)
                        .size("95")
                        .build();
        
        basketService.addBasket("adminA2",35L,basketForm);

        product = productService.findProductById(35L);
        int updatedProduct = product.getAmount();

        System.out.println("updatedProduct = " + updatedProduct);

        assertThat(amount).isEqualTo(updatedProduct + selectedCount);
    }


    @Test
    public void 상품조회한다(){
        form = ProductForm.
            builder()
            .amount(1000)
            .name(productName)
            .description("빨간 상의 입니다")
            .price(10000)
            .categoryName("BEST")
            .build();

        productService.addProduct(form);


        Product foundProduct = productRepository.findByName(productName);
        System.out.println("foundProduct = " + foundProduct);
        assertThat(foundProduct.getName()).isEqualTo(productName);
    }


    @Autowired
    JwtTokenProvider jwtTokenProvider;





    @Autowired
    UserService userService;

    @Test
    public void updateProductCntInBasket(){
        Basket basket = userService.findBasketByBasketId(14L);

        BasketDTO basketDTO = BasketDTO.createBasketDTO(basket);
        System.out.println("basketDTO = " + basketDTO);

        int adminA2 = basketService.changeProductCntInBasket( 14L, 1);
        System.out.println("adminA2 = " + adminA2);
    }









}