package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.entity.Order;
import com.hyderabad_home_theaters.repository.OrderRepository;
import com.hyderabad_home_theaters.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product.");
        }

        order.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus("PENDING");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUser_UserId(userId); // Assuming entity has `user.userId`
    }
}
