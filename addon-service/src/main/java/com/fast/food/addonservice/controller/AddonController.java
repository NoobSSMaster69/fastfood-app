package com.fast.food.addonservice.controller;

import com.fast.food.addonservice.entity.Addon;
import com.fast.food.addonservice.service.AddonService;
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
@RequestMapping("/api/v1/addons") // Базовый URL для контроллера
@Validated
public class AddonController {
    private final Logger logger = LoggerFactory.getLogger(AddonController.class);
    private final AddonService addonService;

    public AddonController(AddonService addonService) {
        this.addonService = addonService; // Dependency injection of the service
    }

    // 1. Create a new addon
    @PostMapping
    public ResponseEntity<Addon> createAddon(@Valid @RequestBody Addon addon) {
        logger.info("Creating a new addon: {}", addon); // Log the creation action
        Addon createdAddon = addonService.createAddon(addon); // Call service to create addon
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddon); // Return created addon
    }

    // 2. Fetch an addon by ID
    @GetMapping("/{id}")
    public ResponseEntity<Addon> getAddonById(@PathVariable("id") Long addonId) {
        logger.info("Fetching addon with ID: {}", addonId); // Log the fetching action
        Addon addon = addonService.getAddonById(addonId) // Call service to fetch addon
                .orElseThrow(() -> new NoSuchElementException("Addon not found with id: " + addonId));
        return ResponseEntity.ok(addon); // Return the found addon
    }

    // 3. Fetch all addons
    @GetMapping
    public ResponseEntity<List<Addon>> getAllAddons() {
        logger.info("Fetching all addons"); // Log the fetching action
        List<Addon> addons = addonService.getAllAddons(); // Call service to fetch all addons
        return ResponseEntity.ok(addons); // Return the list of addons
    }

    // 4. Update an existing addon
    @PutMapping("/{id}")
    public ResponseEntity<Addon> updateAddon(
            @PathVariable("id") Long addonId,
            @Valid @RequestBody Addon updatedAddon) {
        logger.info("Updating addon with ID {}: {}", addonId, updatedAddon); // Log the update action
        ResponseEntity<Addon> updated = addonService.updateAddon(addonId, updatedAddon); // Call service to update addon
        return updated; // Return the updated addon
    }

    // 5. Delete an addon by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddon(@PathVariable("id") Long addonId) {
        logger.info("Deleting addon with ID: {}", addonId); // Log the delete action
        ResponseEntity<Void> response = addonService.deleteAddon(addonId); // Call service to delete addon
        return response; // Return the response
    }

    // 6. Fetch addons by category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Addon>> getAddonsByCategory(@PathVariable("categoryId") Long categoryId) {
        logger.info("Fetching addons for category ID: {}", categoryId); // Log the fetching action
        List<Addon> addons = addonService.getAddonsByCategory(categoryId); // Call service to fetch addons by category
        return ResponseEntity.ok(addons); // Return the list of addons
    }

    // 7. Check if an addon exists by ID
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkAddonExists(@PathVariable("id") Long addonId) {
        logger.info("Checking existence of addon with ID: {}", addonId); // Log the check action
        boolean exists = addonService.existsById(addonId); // Call service to check existence
        return ResponseEntity.ok(exists); // Return existence status
    }

    // 8. Fetch addons by a list of IDs
    @PostMapping("/bulk")
    public ResponseEntity<List<Addon>> getAddonsByIds(@RequestBody List<Long> ids) {
        logger.info("Fetching addons for IDs: {}", ids); // Log the fetching action
        List<Addon> addons = addonService.getAddonsByIds(ids); // Call service to fetch addons by IDs
        return ResponseEntity.ok(addons); // Return the list of addons
    }

    // 9. Delete all addons (for testing or cleanup purposes)
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllAddons() {
        logger.info("Deleting all addons"); // Log the delete all action
        addonService.deleteAllAddons(); // Call service to delete all addons
        return ResponseEntity.noContent().build(); // Return no content response
    }

    // Exception handler for NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        logger.error("Error: {}", ex.getMessage()); // Log error message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // Return 404 response
    }

    // Exception handler for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Error: {}", ex.getMessage()); // Log error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // Return 400 response
    }

    // Generic exception handler for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage()); // Log error message
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred."); // Return 500 response
    }
}

