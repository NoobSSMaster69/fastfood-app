package com.fast.food.basketservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long dishId; // Идентификатор блюда

    private int quantity; // Количество

    private BigDecimal unitPrice; // Цена за единицу

    @ElementCollection
    private List<Long> ingredientsToRemove; // Список ингредиентов для удаления

    @ElementCollection
    private List<Long> ingredientsToAdd; // Список ингредиентов для добавления

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket; // Связь с корзиной

    public BasketItem() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Long getDishId() {
        return dishId;
    }

    public void setDishId(@NotNull Long dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<Long> getIngredientsToRemove() {
        return ingredientsToRemove;
    }

    public void setIngredientsToRemove(List<Long> ingredientsToRemove) {
        this.ingredientsToRemove = ingredientsToRemove;
    }

    public List<Long> getIngredientsToAdd() {
        return ingredientsToAdd;
    }

    public void setIngredientsToAdd(List<Long> ingredientsToAdd) {
        this.ingredientsToAdd = ingredientsToAdd;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
