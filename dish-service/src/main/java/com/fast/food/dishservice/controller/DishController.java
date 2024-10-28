package com.fast.food.dishservice.controller;

import com.fast.food.dishservice.entity.Dish;
import com.fast.food.dishservice.service.DishService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/dishes")
@Validated
public class DishController {

    private final Logger logger = LoggerFactory.getLogger(DishController.class);
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService; // Dependency injection of the service
    }

    // Create a new dish
    @PostMapping
    public ResponseEntity<Dish> createDish(@Valid @RequestBody Dish dish) {
        logger.info("Creating a new dish: {}", dish);
        Dish createdDish = dishService.createDish(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDish);
    }

    // Get all dishes
    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        logger.info("Fetching all dishes.");
        List<Dish> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    // Get a dish by ID
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable("id") Long dishId) {
        logger.info("Fetching dish with ID {}", dishId);
        Dish dish = dishService.getDishById(dishId).orElseThrow(() -> new NoSuchElementException("Dish not found with id: " + dishId));
        return ResponseEntity.ok(dish);
    }

    // Update a dish
    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") Long dishId, @Valid @RequestBody Dish updatedDish) {
        logger.info("Updating dish with ID {}: {}", dishId, updatedDish);
        Dish dish = dishService.updateDish(dishId, updatedDish).getBody();
        return ResponseEntity.ok(dish);
    }

    // Delete a dish
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable("id") Long dishId) {
        logger.info("Deleting dish with ID {}", dishId);
        String result = dishService.deleteDish(dishId).getStatusCode().toString();
        return ResponseEntity.ok(result);
    }

    // Get ingredient IDs for a dish
    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<Long>> getIngredientIdsByDishId(@PathVariable("id") Long dishId) {
        logger.info("Fetching ingredient IDs for dish with ID {}", dishId);
        List<Long> ingredientIds = dishService.getIngredientIdsByDishId(dishId);
        return ResponseEntity.ok(ingredientIds);
    }

    // Exception handling for NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        logger.error("Error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Exception handling for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Exception handling for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
