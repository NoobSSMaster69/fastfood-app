package com.fast.food.saleservice.repository;

import com.fast.food.saleservice.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findByTypeAndTypeId(Discount.DiscountType type, Long typeId);
    List<Discount> findByEndTimeAfterOrEndTimeIsNull(LocalDateTime currentTime);
}
