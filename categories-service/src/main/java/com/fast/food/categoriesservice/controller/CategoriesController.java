package com.fast.food.categoriesservice.controller;

import com.fast.food.categoriesservice.entity.Categories;
import com.fast.food.categoriesservice.service.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);
    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    // Создание новой категории
    @PostMapping
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        try {
            Categories createdCategory = categoriesService.createCategory(category);
            logger.info("Category created with id: {}", createdCategory.getId());
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("Failed to create category", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Получение всех категорий
    @GetMapping
    public ResponseEntity<List<Categories>> getAllCategories() {
        try {
            List<Categories> categories = categoriesService.getAllCategories();
            logger.info("Retrieved {} categories", categories.size());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve categories", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Получение категории по ID
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        try {
            Optional<Categories> category = categoriesService.getCategoryById(id);
            return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve category with id: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Обновление категории по ID
    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable Long id, @RequestBody Categories category) {
        try {
            Optional<Categories> updatedCategory = categoriesService.updateCategory(id, category);
            return updatedCategory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            logger.error("Failed to update category with id: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Удаление категории по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            if (categoriesService.deleteCategory(id)) {
                logger.info("Category with id: {} deleted", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.warn("Category with id: {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            logger.error("Failed to delete category with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
