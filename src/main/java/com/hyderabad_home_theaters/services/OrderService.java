package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Order order);
    List<Order> getUserOrders(Long userId);
}
