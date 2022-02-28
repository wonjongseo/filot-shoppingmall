package com.filot.filotshop.controller;


import com.filot.filotshop.dto.basket.BasketDTO;
import com.filot.filotshop.dto.basket.BasketForm;
import com.filot.filotshop.dto.product.ProductForm;
import com.filot.filotshop.dto.user.JoinForm;
import com.filot.filotshop.entity.Basket;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.entity.User;
import com.filot.filotshop.service.BasketService;
import com.filot.filotshop.service.ProductService;
import com.filot.filotshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BasketServiceTest {

    @Autowired  BasketService basketService;

    @Autowired  UserService userService;

    @Autowired  ProductService productService;

    public static int selectProductCnt = 10;

    public static BasketForm basketForm = BasketForm
            .builder()
            .count(selectProductCnt)
            .color("blue")
            .size("100")
            .build();

    ProductForm productForm;
    JoinForm userForm;
    Product product;
    User user;
    @BeforeEach
    public void setUp(){
        productForm = ProductServiceTest.productForm ;
        userForm = UserServiceTest.userForm;
        user = userService.join(userForm);
        product = productService.addProduct(productForm);
    }

    @Test
    public void 유저의_모든_장바구니_조회한다(){
        basketService.addBasket(user.getEmail(), product.getId(), basketForm);

        List<BasketDTO> allBasket = basketService.getAllBasket(user.getEmail());
        assertThat(allBasket.size()).isEqualTo(1);
    }


    @Test
    public void 상품_장바구니에_넣는다(){
        // 상품 객체 총 수량 감소한다.
        int productCnt = productForm.getAmount();


        basketService.addBasket(user.getEmail(), product.getId(), basketForm);

        assertThat(user.getBaskets().size()).isEqualTo(1);
        assertThat(productCnt).isEqualTo(basketForm.getCount() + product.getAmount());
    }



    @Test
    public void 장바구니에_있는_상품_개수_바꾼다(){

        int productCnt = productForm.getAmount();

        Basket basket = basketService.addBasket(user.getEmail(), product.getId(), basketForm);

        int productCount = basket.getProductCount();

        assertThat(productCnt).isEqualTo(productCount + product.getAmount());

        int updatedProductCount = userService.changeProductCount(user.getEmail(), basket.getId(), 20);


        assertThat(productCnt).isEqualTo(updatedProductCount + product.getAmount());
    }


}
