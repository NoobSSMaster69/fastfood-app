package com.fast.food.reviewservice.repository;

import com.fast.food.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
