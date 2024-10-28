package com.fast.food.addonservice.service;

import com.fast.food.addonservice.repository.AddonRepository;
import com.fast.food.addonservice.entity.Addon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddonService {

    private final AddonRepository addonRepository; // Repository for accessing addon data
    private final Logger logger = LoggerFactory.getLogger(AddonService.class); // Logger for this service

    @Autowired
    public AddonService(AddonRepository addonRepository) {
        this.addonRepository = addonRepository; // Dependency injection of the repository
    }

    // Create a new addon
    public Addon createAddon(Addon addon) {
        logger.info("Creating new addon: {}", addon); // Log the creation action
        return addonRepository.save(addon); // Save and return the newly created addon
    }

    // Fetch an addon by its ID
    public Optional<Addon> getAddonById(Long id) {
        logger.info("Fetching addon with id: {}", id); // Log the fetching action
        return addonRepository.findById(id); // Return the addon if found
    }

    // Fetch all addons
    public List<Addon> getAllAddons() {
        logger.info("Fetching all addons"); // Log the action of fetching all addons
        return addonRepository.findAll(); // Return the list of all addons
    }

    // Update an existing addon
    public ResponseEntity<Addon> updateAddon(Long id, Addon addonDetails) {
        logger.info("Updating addon with id: {}", id); // Log the update action
        try {
            // Find the addon by ID and update its details if it exists
            return addonRepository.findById(id)
                    .map(addon -> {
                        updateAddonDetails(addon, addonDetails); // Update the addon details
                        return ResponseEntity.ok(addonRepository.save(addon)); // Save and return the updated addon
                    })
                    .orElseThrow(() -> {
                        logger.error("Addon with id {} not found", id); // Log error if not found
                        return new RuntimeException("Addon not found with id: " + id); // Throw runtime exception
                    });
        } catch (RuntimeException e) {
            logger.error("Error updating addon with id {}: {}", id, e.getMessage()); // Log any errors during update
            throw e; // Re-throw exception for higher-level handling
        }
    }

    // Delete an addon by its ID
    public ResponseEntity<Void> deleteAddon(Long id) {
        logger.info("Deleting addon with id: {}", id); // Log the delete action
        try {
            if (!addonRepository.existsById(id)) { // Check if the addon exists
                logger.error("Addon with id {} not found", id); // Log error if not found
                throw new RuntimeException("Addon not found with id: " + id); // Throw runtime exception
            }
            addonRepository.deleteById(id); // Delete the addon
            return ResponseEntity.noContent().build(); // Return a response indicating no content
        } catch (RuntimeException e) {
            logger.error("Error deleting addon with id {}: {}", id, e.getMessage()); // Log any errors during deletion
            throw e; // Re-throw exception for higher-level handling
        }
    }

    // Fetch addons by category ID
    public List<Addon> getAddonsByCategory(Long categoryId) {
        logger.info("Fetching addons for category id: {}", categoryId); // Log the fetching action
        return addonRepository.findByCategoryIdsContains(categoryId); // Return addons belonging to the specified category
    }

    // Check if an addon exists by ID
    public boolean existsById(Long id) {
        logger.info("Checking existence of addon with id: {}", id); // Log the check action
        return addonRepository.existsById(id); // Return true if it exists, false otherwise
    }

    // Delete all addons (for testing or cleanup purposes)
    public void deleteAllAddons() {
        logger.info("Deleting all addons"); // Log the delete all action
        addonRepository.deleteAll(); // Delete all addons from the repository
    }

    // Fetch addons by a list of IDs
    public List<Addon> getAddonsByIds(List<Long> ids) {
        logger.info("Fetching addons for ids: {}", ids); // Log the fetching action
        return addonRepository.findAllById(ids); // Return addons corresponding to the specified IDs
    }

    // Helper method to update addon details
    private void updateAddonDetails(Addon addon, Addon addonDetails) {
        addon.setName(addonDetails.getName()); // Update the name
        addon.setPrice(addonDetails.getPrice()); // Update the price
        addon.setCategoryIds(addonDetails.getCategoryIds()); // Update the category IDs
    }
}
