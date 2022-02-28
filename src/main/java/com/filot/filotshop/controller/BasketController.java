package com.filot.filotshop.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.dto.basket.BasketDTO;
import com.filot.filotshop.dto.basket.BasketForm;
import com.filot.filotshop.repository.BasketRepository;
import com.filot.filotshop.repository.UserRepository;
import com.filot.filotshop.repository.product.ProductRepository;
import com.filot.filotshop.service.BasketService;
import com.filot.filotshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final UserService userService;
    private final BasketService basketService;
    private final JwtTokenProvider jwtTokenProvider;


//ok
    @PostMapping("/products/{id}/basket")
    @Transactional
    public void addProductInToBasket(HttpServletRequest req, @PathVariable(name = "id") Long productId , @RequestBody BasketForm basketForm) {
        String userEmail = jwtTokenProvider.getUserEmail(req);

        if(userEmail== null && userEmail.equals("")){
            return;
        }

        basketService.addBasket(userEmail, productId,basketForm);
    }


}
