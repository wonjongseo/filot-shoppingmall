package com.filot.filotshop.basket.controller;

import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.basket.entity.BasketForm;
import com.filot.filotshop.basket.service.BasketService;
import com.filot.filotshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/products/{id}/baskets")
//    @Transactional
    public ResponseEntity addProductInToBasket(HttpServletRequest req, @PathVariable(name = "id") Long productId , @RequestBody BasketForm basketForm) {
        String userEmail = jwtTokenProvider.getUserEmail(req);

        if(userEmail== null && userEmail.equals("")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        basketService.addBasket(userEmail, productId, basketForm);

        return ResponseEntity.ok(basketForm);
    }


    @DeleteMapping("/users/baskets/{basket-id}")
    public void deleteBasket(@PathVariable(name = "basket-id") Long basketId){
        basketService.deleteBasket(basketId);
    }

    // ok
    @PutMapping("/users/baskets/{baskets-id}")
    public void updateProductCnt(
            @PathVariable(name = "baskets-id") Long basketId, @RequestParam(name = "cnt", required = false) int cnt) {

        basketService.changeProductCntInBasket(basketId, cnt);
    }



}
