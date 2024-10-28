package com.fast.food.basketservice.service;

import com.fast.food.basketservice.entity.Basket;
import com.fast.food.basketservice.repository.BasketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService {
    private static final Logger logger = LoggerFactory.getLogger(BasketService.class);
    private final BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket createBasket(Long userId) {
        Basket basket = new Basket();
        basket.setUserId(userId);
        logger.info("Creating a new basket for userId: {}", userId);
        return basketRepository.save(basket);
    }

    public Basket getBasketByUserId(Long userId) {
        logger.info("Fetching basket for userId: {}", userId);
        return Optional.ofNullable(basketRepository.findByUserId(userId))
                .orElseThrow(() -> new RuntimeException("Basket not found for userId: " + userId));
    }

    public void deleteBasket(Long basketId) {
        try {
            logger.info("Deleting basket with id: {}", basketId);
            basketRepository.deleteById(basketId);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Basket not found with id: {}", basketId, e);
            throw new RuntimeException("Basket not found for id: " + basketId);
        }
    }
}
