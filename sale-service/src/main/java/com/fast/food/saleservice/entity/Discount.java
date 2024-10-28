package com.fast.food.saleservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @NotNull
    @Column(nullable = false)
    private Long typeId;

    private String name;

    private String description;

    @Min(0)
    @Max(100)
    @Column(precision = 5, scale = 2)
    private BigDecimal discount;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createTime;

    private LocalDateTime endTime;

    public Discount() {
    }

    public enum DiscountType {
        CATEGORY, USER, DISH
    }

    @PrePersist
    protected void onCreate() {
        if (this.createTime == null) {
            this.createTime = LocalDateTime.now();
        }
    }

    // Приватный конструктор для билдера
    private Discount(Builder builder) {
        this.type = builder.type;
        this.typeId = builder.typeId;
        this.name = builder.name;
        this.description = builder.description;
        this.discount = builder.discount;
        this.createTime = builder.createTime;
        this.endTime = builder.endTime;
    }

    // Статический класс Builder
    public static class Builder {
        private final DiscountType type;
        private final Long typeId;
        private String name;
        private String description;
        private BigDecimal discount;
        private LocalDateTime createTime;
        private LocalDateTime endTime;

        public Builder(DiscountType type, Long typeId) {
            this.type = type;
            this.typeId = typeId;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Discount build() {
            return new Discount(this);
        }
    }

    public @NotNull DiscountType getType() {
        return type;
    }

    public void setType(@NotNull DiscountType type) {
        this.type = type;
    }

    public @NotNull Long getTypeId() {
        return typeId;
    }

    public void setTypeId(@NotNull Long typeId) {
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @Min(0) @Max(100) BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(@Min(0) @Max(100) BigDecimal discount) {
        this.discount = discount;
    }

    @NotNull
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(@NotNull LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
