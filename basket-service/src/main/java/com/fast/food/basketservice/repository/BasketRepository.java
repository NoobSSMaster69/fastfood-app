package com.fast.food.basketservice.repository;

import com.fast.food.basketservice.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByUserId(Long userId);
}
