package com.fast.food.orderservice.controller;

import com.fast.food.orderservice.entity.Order;
import com.fast.food.orderservice.entity.OrderStatus;
import com.fast.food.orderservice.entity.PaymentMethod;
import com.fast.food.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Создание нового заказа
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long userId,
                                             @RequestParam Long cartId,
                                             @RequestParam BigDecimal totalAmount,
                                             @RequestParam PaymentMethod paymentMethod) {
        Order order = orderService.createOrder(userId, cartId, totalAmount, paymentMethod);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Получение заказа по ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Получение всех заказов пользователя
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Обновление статуса заказа
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Удаление заказа
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
