package com.fast.food.categoriesservice.service;

import com.fast.food.categoriesservice.entity.Categories;
import com.fast.food.categoriesservice.repository.CategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);
    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    // Создание новой категории
    public Categories createCategory(Categories category) {
        try {
            logger.info("Creating a new category with name: {}", category.getName());
            return categoriesRepository.save(category);
        } catch (DataAccessException e) {
            logger.error("Failed to create category with name: {}", category.getName(), e);
            throw new RuntimeException("Failed to create category", e);
        }
    }

    // Обновление категории
    public Optional<Categories> updateCategory(Long id, Categories category) {
        return categoriesRepository.findById(id).map(existingCategory -> {
            if (category.getName() != null) {
                existingCategory.setName(category.getName());
            }
            try {
                logger.info("Updating category with id: {}", id);
                return categoriesRepository.save(existingCategory);
            } catch (DataAccessException e) {
                logger.error("Failed to update category with id: {}", id, e);
                throw new RuntimeException("Failed to update category", e);
            }
        });
    }

    // Получение всех категорий
    public List<Categories> getAllCategories() {
        try {
            logger.info("Retrieving all categories");
            return categoriesRepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve categories", e);
            throw new RuntimeException("Failed to retrieve categories", e);
        }
    }

    // Получение категории по ID
    public Optional<Categories> getCategoryById(Long id) {
        try {
            logger.info("Retrieving category with id: {}", id);
            return categoriesRepository.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve category with id: {}", id, e);
            throw new RuntimeException("Failed to retrieve category", e);
        }
    }

    // Удаление категории
    public boolean deleteCategory(Long id) {
        try {
            if (categoriesRepository.existsById(id)) {
                categoriesRepository.deleteById(id);
                logger.info("Deleted category with id: {}", id);
                return true;
            } else {
                logger.warn("Category with id: {} does not exist", id);
                return false;
            }
        } catch (DataAccessException e) {
            logger.error("Failed to delete category with id: {}", id, e);
            throw new RuntimeException("Failed to delete category", e);
        }
    }
}
