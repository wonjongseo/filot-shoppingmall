package com.filot.filotshop.basket.repository;

import com.filot.filotshop.basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
