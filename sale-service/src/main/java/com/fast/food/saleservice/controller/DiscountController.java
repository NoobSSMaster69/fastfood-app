package com.fast.food.saleservice.controller;

import com.fast.food.saleservice.entity.Discount;
import com.fast.food.saleservice.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {

    private final DiscountService discountService;
    private final Logger logger = LoggerFactory.getLogger(DiscountController.class);

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // Создание новой скидки
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        logger.info("Request to create discount: {}", discount);
        try {
            Discount createdDiscount = discountService.createDiscount(discount);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating discount: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Обновление существующей скидки
    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Long id, @RequestBody Discount discount) {
        logger.info("Request to update discount with id {}: {}", id, discount);
        Optional<Discount> updatedDiscount = discountService.updateDiscount(id, discount);
        return updatedDiscount.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Discount with id {} not found for update.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Получение всех скидок
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        logger.info("Request to retrieve all discounts.");
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    // Получение скидки по ID
    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
        logger.info("Request to retrieve discount with id: {}", id);
        return discountService.getDiscountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Discount with id {} not found.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Удаление скидки по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        logger.info("Request to delete discount with id: {}", id);
        if (discountService.deleteDiscount(id)) {
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Discount with id {} not found for deletion.", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Получение активных скидок
    @GetMapping("/active")
    public ResponseEntity<List<Discount>> getActiveDiscounts() {
        logger.info("Request to retrieve active discounts.");
        List<Discount> activeDiscounts = discountService.getActiveDiscounts();
        return ResponseEntity.ok(activeDiscounts);
    }

    // Получение скидок по типу и ID
    @GetMapping("/type/{type}/id/{typeId}")
    public ResponseEntity<List<Discount>> getDiscountsByTypeAndId(@PathVariable Discount.DiscountType type,
                                                                  @PathVariable Long typeId) {
        logger.info("Request to retrieve discounts by type {} and typeId {}.", type, typeId);
        List<Discount> discounts = discountService.getDiscountsByTypeAndId(type, typeId);
        return ResponseEntity.ok(discounts);
    }
}
