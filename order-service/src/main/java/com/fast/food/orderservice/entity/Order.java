package com.fast.food.orderservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;  // Идентификатор пользователя

    @NotNull
    private Long cartId;  // Идентификатор корзины, связанной с этим заказом

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // Выбранный способ оплаты

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Статус заказа (например, CREATED, PAID, DELIVERED и т.д.)

    private BigDecimal totalAmount; // Итоговая стоимость заказа

    private LocalDateTime orderDate; // Дата создания заказа

    public Order() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }

    public @NotNull Long getCartId() {
        return cartId;
    }

    public void setCartId(@NotNull Long cartId) {
        this.cartId = cartId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}



