package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // Correct query method: Find orders by the User's ID
//    List<Order> findByUser_UserId(Long userId);

    Orders findByRazorpayOrderId(String orderId);
}
