package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Correct query method: Find orders by the User's ID
    List<Order> findByUser_UserId(Long userId);  // This is the correct method

    Order findByRazorpayOrderId(String orderId);
}
