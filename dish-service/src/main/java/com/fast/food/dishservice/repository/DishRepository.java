package com.fast.food.dishservice.repository;

import com.fast.food.dishservice.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish,Long> {
}
