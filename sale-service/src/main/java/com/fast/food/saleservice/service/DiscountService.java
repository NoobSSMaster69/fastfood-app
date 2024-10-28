package com.fast.food.saleservice.service;

import com.fast.food.saleservice.entity.Discount;
import com.fast.food.saleservice.repository.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final Logger logger = LoggerFactory.getLogger(DiscountService.class);

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    // Метод для создания новой скидки
    public Discount createDiscount(Discount discount) {
        validateDiscount(discount); // Валидация перед сохранением
        logger.info("Creating a new discount: {}", discount);
        return discountRepository.save(discount);
    }

    // Метод для обновления скидки
    public Optional<Discount> updateDiscount(Long id, Discount discount) {
        return discountRepository.findById(id).map(existingDiscount -> {
            updateIfNotNull(existingDiscount::setType, discount.getType());
            updateIfNotNull(existingDiscount::setTypeId, discount.getTypeId());
            updateIfNotNull(existingDiscount::setName, discount.getName());
            updateIfNotNull(existingDiscount::setDescription, discount.getDescription());
            updateIfNotNull(existingDiscount::setDiscount, discount.getDiscount());
            updateIfNotNull(existingDiscount::setCreateTime, discount.getCreateTime());
            updateIfNotNull(existingDiscount::setEndTime, discount.getEndTime());

            validateDiscount(existingDiscount);
            logger.info("Updating discount with id {}: {}", id, existingDiscount);
            return discountRepository.save(existingDiscount);
        });
    }

    // Вспомогательный метод для обновления полей
    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    // Метод для валидации скидки
    private void validateDiscount(Discount discount) {
        // Проверка на диапазон скидки
        if (discount.getDiscount() != null && (discount.getDiscount().compareTo(BigDecimal.ZERO) < 0 || discount.getDiscount().compareTo(BigDecimal.valueOf(100)) > 0)) {
            logger.error("Discount must be in the range of 0 to 100.");
            throw new IllegalArgumentException("Discount must be in the range of 0 to 100.");
        }

        // Проверка, чтобы время начала не было больше времени окончания
        if (discount.getEndTime() != null && discount.getCreateTime() != null) {
            if (discount.getCreateTime().isAfter(discount.getEndTime())) {
                logger.error("Start time cannot be later than end time.");
                throw new IllegalArgumentException("Start time cannot be later than end time.");
            }
        }
    }

    // Метод для получения всех скидок
    public List<Discount> getAllDiscounts() {
        logger.info("Retrieving all discounts.");
        return discountRepository.findAll();
    }

    // Метод для получения скидки по идентификатору
    public Optional<Discount> getDiscountById(Long id) {
        logger.info("Retrieving discount with id: {}", id);
        return discountRepository.findById(id);
    }

    // Метод для удаления скидки по идентификатору
    public boolean deleteDiscount(Long id) {
        if (discountRepository.existsById(id)) {
            logger.info("Deleting discount with id: {}", id);
            discountRepository.deleteById(id);
            return true;
        }
        logger.warn("Discount with id {} does not exist.", id);
        return false;
    }

    // Получение активных скидок (по текущему времени)
    public List<Discount> getActiveDiscounts() {
        logger.info("Retrieving active discounts.");
        return discountRepository.findByEndTimeAfterOrEndTimeIsNull(LocalDateTime.now());
    }

    // Получение скидок по типу и ID
    public List<Discount> getDiscountsByTypeAndId(Discount.DiscountType type, Long typeId) {
        logger.info("Retrieving discounts by type {} and typeId {}.", type, typeId);
        return discountRepository.findByTypeAndTypeId(type, typeId);
    }
}