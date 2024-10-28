package com.fast.food.dishservice.service;

import com.fast.food.dishservice.entity.Dish;
import com.fast.food.dishservice.repository.DishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository; // Repository for accessing dish data
    private final Logger logger = LoggerFactory.getLogger(DishService.class); // Logger for this service

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository; // Dependency injection of the repository
    }

    // Create a new dish
    public Dish createDish(Dish dish) {
        logger.info("Creating new dish: {}", dish); // Log the creation action
        return dishRepository.save(dish); // Save and return the newly created dish
    }

    // Fetch all dishes
    public List<Dish> getAllDishes() {
        logger.info("Fetching all dishes"); // Log the action of fetching all dishes
        return dishRepository.findAll(); // Return the list of all dishes
    }

    // Fetch a dish by its ID
    public Optional<Dish> getDishById(Long id) {
        logger.info("Fetching dish with id: {}", id); // Log the fetching action
        return dishRepository.findById(id); // Return the dish if found
    }

    // Update an existing dish
    public ResponseEntity<Dish> updateDish(Long id, Dish dishDetails) {
        logger.info("Updating dish with id: {}", id); // Log the update action
        try {
            return dishRepository.findById(id)
                    .map(dish -> {
                        updateDishDetails(dish, dishDetails); // Update the dish details
                        return ResponseEntity.ok(dishRepository.save(dish)); // Save and return the updated dish
                    })
                    .orElseThrow(() -> {
                        logger.error("Dish with id {} not found", id); // Log error if not found
                        return new RuntimeException("Dish not found with id: " + id); // Throw runtime exception
                    });
        } catch (RuntimeException e) {
            logger.error("Error updating dish with id {}: {}", id, e.getMessage()); // Log any errors during update
            throw e; // Re-throw exception for higher-level handling
        }
    }

    // Delete a dish by its ID
    public ResponseEntity<Void> deleteDish(Long id) {
        logger.info("Deleting dish with id: {}", id); // Log the delete action
        try {
            if (!dishRepository.existsById(id)) { // Check if the dish exists
                logger.error("Dish with id {} not found", id); // Log error if not found
                throw new RuntimeException("Dish not found with id: " + id); // Throw runtime exception
            }
            dishRepository.deleteById(id); // Delete the dish
            return ResponseEntity.noContent().build(); // Return a response indicating no content
        } catch (RuntimeException e) {
            logger.error("Error deleting dish with id {}: {}", id, e.getMessage()); // Log any errors during deletion
            throw e; // Re-throw exception for higher-level handling
        }
    }

    // Fetch ingredient IDs for a dish by ID
    public List<Long> getIngredientIdsByDishId(Long dishId) {
        logger.info("Fetching ingredient IDs for dish with id: {}", dishId); // Log the fetching action
        return dishRepository.findById(dishId)
                .map(Dish::getIngredientIds) // Extract ingredient IDs if the dish is found
                .orElseThrow(() -> new RuntimeException("Dish not found with id: " + dishId)); // Handle not found case
    }

    // Check if a dish exists by ID
    public boolean existsById(Long id) {
        logger.info("Checking existence of dish with id: {}", id); // Log the check action
        return dishRepository.existsById(id); // Return true if it exists, false otherwise
    }

    // Delete all dishes (for testing or cleanup purposes)
    public void deleteAllDishes() {
        logger.info("Deleting all dishes"); // Log the delete all action
        dishRepository.deleteAll(); // Delete all dishes from the repository
    }

    // Helper method to update dish details
    private void updateDishDetails(Dish dish, Dish dishDetails) {
        dish.setName(dishDetails.getName()); // Update the name
        dish.setPrice(dishDetails.getPrice()); // Update the price
        dish.setWeight(dishDetails.getWeight()); // Update the weight
        dish.setDescription(dishDetails.getDescription());
        dish.setIngredientIds(dishDetails.getIngredientIds()); // Update the ingredient IDs
    }
}
