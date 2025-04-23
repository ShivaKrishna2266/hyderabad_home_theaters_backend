package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.OrderDTO;
import com.hyderabad_home_theaters.DTOs.payment.OrderRequest;
import com.hyderabad_home_theaters.entity.Orders;
import com.hyderabad_home_theaters.mapper.OrderMapper;
import com.hyderabad_home_theaters.repository.OrderRepository;
import com.hyderabad_home_theaters.util.Signature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Chinna
 *
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Transactional
    public Orders saveOrder(final String razorpayOrderId, OrderRequest orderRequest) {
        Orders order = new Orders();
        order.setRazorpayOrderId(razorpayOrderId);
        order.setUserId(orderRequest.getUserId());
        order.setAmount(orderRequest.getAmount());
//        order.setProfile(orderRequest.getProfile().toString());
        order.setEmail(orderRequest.getEmail());
        order.setMobileNumber(orderRequest.getMobileNumber());
        order.setCustomerName(orderRequest.getCustomerName());
        return orderRepository.save(order);
    }

    @Transactional
    public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret) {
        String errorMsg = null;
        try {
            Orders order = orderRepository.findByRazorpayOrderId(razorpayOrderId);
            // Verify if the razorpay signature matches the generated one to
            // confirm the authenticity of the details returned
            String generatedSignature = Signature.calculateRFC2104HMAC(order.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
            if (generatedSignature.equals(razorpaySignature)) {
                order.setRazorpayOrderId(razorpayOrderId);
                order.setRazorpayPaymentId(razorpayPaymentId);
                order.setRazorpaySignature(razorpaySignature);
                orderRepository.save(order);
            } else {
                errorMsg = "Payment validation failed: Signature doesn't match";
            }
        } catch (Exception e) {
            log.error("Payment validation failed", e);
            errorMsg = e.getMessage();
        }
        return errorMsg;
    }

    @Transactional
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::convertToDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public OrderDTO getOrderByStatus(OrderDTO orderDTO) {
        Orders existingOrder = orderRepository.findByRazorpayOrderId(orderDTO.getRazorpayOrderId());
        if(existingOrder!=null){
            existingOrder.setOrderId(existingOrder.getOrderId());
        }
        existingOrder.setOrderStatus(orderDTO.getOrderStatus());
        existingOrder = orderRepository.save(existingOrder);
        return 	 OrderMapper.convertToDTO(existingOrder);
    }
}
