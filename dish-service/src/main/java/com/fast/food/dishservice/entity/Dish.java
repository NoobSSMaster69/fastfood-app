package com.fast.food.dishservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the dish

    @NotNull
    @Column(nullable = false)
    private String name; // Name of the dish

    @NotNull
    @Column(nullable = false)
    private BigDecimal price; // Price of the dish

    private Double weight; // Weight of the dish in grams

    private Double diameter;

    private Double mills;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    @ElementCollection
    private List<Long> ingredientIds; // List of ingredient IDs associated with the dish

    @NotNull
    @Column(nullable = false)
    @ElementCollection
    private List<Long> categoriesIds; // List of category IDs associated with the dish

    // Constructors
    public Dish() {
    }

    public Dish(String name, BigDecimal price, Double weight, List<Long> ingredientIds) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.ingredientIds = ingredientIds;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Double getMills() {
        return mills;
    }

    public void setMills(Double mills) {
        this.mills = mills;
    }

    public List<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters and Setters
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<Long> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(List<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
