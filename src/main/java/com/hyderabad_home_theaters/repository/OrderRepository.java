package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {


    Orders findByRazorpayOrderId(String orderId);

    List<Orders> findOrdersByUserId (Long userId);
}
