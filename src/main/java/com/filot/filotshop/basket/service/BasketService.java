package com.filot.filotshop.basket.service;

import com.filot.filotshop.basket.entity.BasketDTO;
import com.filot.filotshop.basket.entity.BasketForm;
import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.basket.repository.BasketRepository;
import com.filot.filotshop.product.repository.ProductRepository;
import com.filot.filotshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<BasketDTO> getAllBasket(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<BasketDTO> baskets = new ArrayList<>();

        for (Basket basket : user.getBaskets()) {
            BasketDTO basketDTO = BasketDTO.createBasketDTO(basket);

            baskets.add(basketDTO);
        }

        return baskets;
    }

    public Basket addBasket(String userEmail, Long productId, BasketForm basketForm){
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Product product = productRepository.getById(productId);

        Basket basket = Basket.builder()
                .product(product)
                .productSize(basketForm.getSize())
                .productColor(basketForm.getColor())
                .productOrder(null)
                .build();
        basket.setProductCount(basketForm.getCount());
        basket.setUser(user);

        return basketRepository.save(basket);
    }


}
