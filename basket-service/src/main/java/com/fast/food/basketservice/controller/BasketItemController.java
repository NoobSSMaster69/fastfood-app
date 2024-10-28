package com.fast.food.basketservice.controller;

import com.fast.food.basketservice.entity.BasketItem;
import com.fast.food.basketservice.service.BasketItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/basket-items")
public class BasketItemController {

    private static final Logger logger = LoggerFactory.getLogger(BasketItemController.class);

    private final BasketItemService basketItemService;

    @Autowired
    public BasketItemController(BasketItemService basketItemService) {
        this.basketItemService = basketItemService;
    }

    @PostMapping
    public ResponseEntity<BasketItem> addItemToBasket(@RequestParam Long basketId,
                                                      @RequestParam Long dishId,
                                                      @RequestParam int quantity,
                                                      @RequestParam BigDecimal unitPrice,
                                                      @RequestParam List<Long> ingredientsToRemove,
                                                      @RequestParam List<Long> ingredientsToAdd) {
        logger.info("Adding item to basket. BasketId: {}, DishId: {}, Quantity: {}, UnitPrice: {}",
                basketId, dishId, quantity, unitPrice);
        BasketItem basketItem = basketItemService.addItemToBasket(basketId, dishId, quantity, unitPrice, ingredientsToRemove, ingredientsToAdd);
        return new ResponseEntity<>(basketItem, HttpStatus.CREATED);
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<List<BasketItem>> getAllItemsByBasketId(@PathVariable Long basketId) {
        logger.info("Fetching all items for basketId: {}", basketId);
        List<BasketItem> items = basketItemService.getAllItemsByBasketId(basketId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeBasketItem(@PathVariable Long itemId) {
        logger.info("Removing item with id: {}", itemId);
        basketItemService.removeBasketItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear/{basketId}")
    public ResponseEntity<Void> clearBasket(@PathVariable Long basketId) {
        logger.info("Clearing all items from basketId: {}", basketId);
        basketItemService.clearBasket(basketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
