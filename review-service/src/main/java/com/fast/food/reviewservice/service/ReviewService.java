package com.fast.food.reviewservice.service;

import com.fast.food.reviewservice.entity.Review;
import com.fast.food.reviewservice.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Создание отзыва
    public Review createReview(Review review) {
        logger.info("Creating new review for user ID: {}", review.getUserId());
        return reviewRepository.save(review);
    }

    // Получение отзыва по ID
    public Optional<Review> getReviewById(Long id) {
        logger.info("Retrieving review with ID: {}", id);
        return reviewRepository.findById(id);
    }

    // Получение всех отзывов
    public List<Review> getAllReviews() {
        logger.info("Retrieving all reviews");
        return reviewRepository.findAll();
    }

    // Обновление отзыва
    public Optional<Review> updateReview(Long id, Review review) {
        return reviewRepository.findById(id).map(existingReview -> {
            if (review.getUserId() != null) {
                existingReview.setUserId(review.getUserId());
            }
            if (review.getRating() != null) {
                existingReview.setRating(review.getRating());
            }
            if (review.getContent() != null) {
                existingReview.setContent(review.getContent());
            }
            logger.info("Updating review with ID: {}", id);
            return reviewRepository.save(existingReview);
        });
    }

    // Удаление отзыва
    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            logger.info("Deleted review with ID: {}", id);
            return true;
        } else {
            logger.warn("Review with ID: {} not found", id);
            return false;
        }
    }
}
