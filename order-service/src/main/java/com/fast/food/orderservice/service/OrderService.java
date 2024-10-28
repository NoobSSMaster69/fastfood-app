package com.fast.food.orderservice.service;

import com.fast.food.orderservice.entity.Order;
import com.fast.food.orderservice.entity.OrderStatus;
import com.fast.food.orderservice.entity.PaymentMethod;
import com.fast.food.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Создание нового заказа
    @Transactional
    public Order createOrder(Long userId, Long cartId, BigDecimal totalAmount, PaymentMethod paymentMethod) {
        Order order = new Order();
        order.setUserId(userId);
        order.setCartId(cartId);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.CREATED);
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Получение заказа по ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Получение всех заказов пользователя
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Обновление статуса заказа
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Удаление заказа
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
