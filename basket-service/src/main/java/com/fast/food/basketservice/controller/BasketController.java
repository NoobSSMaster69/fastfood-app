package com.fast.food.basketservice.controller;

import com.fast.food.basketservice.entity.Basket;
import com.fast.food.basketservice.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/baskets")
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestParam Long userId) {
        logger.info("Creating basket for userId: {}", userId);
        Basket basket = basketService.createBasket(userId);
        return new ResponseEntity<>(basket, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Basket> getBasketByUserId(@PathVariable Long userId) {
        logger.info("Fetching basket for userId: {}", userId);
        Basket basket = basketService.getBasketByUserId(userId);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @DeleteMapping("/{basketId}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long basketId) {
        logger.info("Deleting basket with id: {}", basketId);
        basketService.deleteBasket(basketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
