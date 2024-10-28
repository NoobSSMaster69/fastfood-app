package com.fast.food.basketservice.service;

import com.fast.food.basketservice.entity.Basket;
import com.fast.food.basketservice.entity.BasketItem;
import com.fast.food.basketservice.repository.BasketItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BasketItemService {
    private static final Logger logger = LoggerFactory.getLogger(BasketItemService.class);
    private final BasketItemRepository basketItemRepository;

    @Autowired
    public BasketItemService(BasketItemRepository basketItemRepository) {
        this.basketItemRepository = basketItemRepository;
    }

    public BasketItem addItemToBasket(Long basketId, Long dishId, int quantity, BigDecimal unitPrice, List<Long> ingredientsToRemove, List<Long> ingredientsToAdd) {
        BasketItem basketItem = new BasketItem();
        basketItem.setDishId(dishId);
        basketItem.setQuantity(quantity);
        basketItem.setUnitPrice(unitPrice);
        basketItem.setIngredientsToRemove(ingredientsToRemove);
        basketItem.setIngredientsToAdd(ingredientsToAdd);

        // Устанавливаем связь с корзиной
        basketItem.setBasket(new Basket());
        basketItem.getBasket().setId(basketId);

        logger.info("Adding item to basketId: {}, dishId: {}", basketId, dishId);
        return basketItemRepository.save(basketItem);
    }

    public List<BasketItem> getAllItemsByBasketId(Long basketId) {
        logger.info("Fetching all items for basketId: {}", basketId);
        return basketItemRepository.findByBasketId(basketId);
    }

    public void removeBasketItem(Long itemId) {
        try {
            logger.info("Removing basket item with id: {}", itemId);
            basketItemRepository.deleteById(itemId);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Basket item not found with id: {}", itemId, e);
            throw new RuntimeException("Basket item not found for id: " + itemId);
        }
    }

    public void clearBasket(Long basketId) {
        logger.info("Clearing all items for basketId: {}", basketId);
        List<BasketItem> items = basketItemRepository.findByBasketId(basketId);
        basketItemRepository.deleteAll(items);
    }
}
