package com.fast.food.basketservice.repository;

import com.fast.food.basketservice.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    List<BasketItem> findByBasketId(Long basketId);
}
