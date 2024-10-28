package com.fast.food.addonservice.repository;

import com.fast.food.addonservice.entity.Addon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddonRepository extends JpaRepository<Addon, Long> {
    List<Addon> findByCategoryIdsContains(Long categoryId);
}
