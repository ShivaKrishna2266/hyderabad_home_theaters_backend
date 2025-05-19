package com.hyderabad_home_theaters.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.OrderDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.DTOs.payment.OrderRequest;
import com.hyderabad_home_theaters.entity.Address;
import com.hyderabad_home_theaters.entity.Customer;
import com.hyderabad_home_theaters.entity.Orders;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.entity.UserProfile;
import com.hyderabad_home_theaters.mapper.OrderMapper;
import com.hyderabad_home_theaters.mapper.UserMapper;
import com.hyderabad_home_theaters.repository.AddressRepository;
import com.hyderabad_home_theaters.repository.CustomerRepository;
import com.hyderabad_home_theaters.repository.OrderRepository;
import com.hyderabad_home_theaters.repository.UserProfileRepository;
import com.hyderabad_home_theaters.util.Signature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Transactional
    public Orders saveOrder(final String razorpayOrderId, OrderRequest orderRequest) {
        if (orderRequest == null) {
            throw new IllegalArgumentException("OrderRequest cannot be null");
        }

        String username = orderRequest.getCustomerName();
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Customer name is missing in orderRequest");
        }

        // Step 1: Check if Customer exists
        Optional<Customer> existingCustomerOpt = customerRepository.findByUsername(username);
        Customer customer;

        if (existingCustomerOpt.isPresent()) {
            customer = existingCustomerOpt.get();
        } else {
            customer = new Customer();
            customer.setUsername(username);
            customer.setFirstName(orderRequest.getProfile().getFirstName());
            customer.setSurname(orderRequest.getProfile().getSurname());
            customer.setFullName(orderRequest.getProfile().getFirstName() + " " + orderRequest.getProfile().getSurname());
            customer.setEmail(orderRequest.getEmail());
            customer.setMobileNumber(orderRequest.getMobileNumber());
            customer.setCreatedBy("SYSTEM");
            customer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            customer = customerRepository.save(customer);
        }

        // Step 2: Check if UserProfile exists
        Optional<UserProfile> existingProfileOpt = userProfileRepository.findByUsername(username);
        UserProfile userProfile;

        if (existingProfileOpt.isPresent()) {
            userProfile = existingProfileOpt.get();
            // Optionally update fields if you want
        } else {
            userProfile = new UserProfile();
            userProfile.setUsername(username);
            userProfile.setEmail(orderRequest.getEmail());
            userProfile.setMobileNumber(orderRequest.getMobileNumber());
            userProfile.setCustomer(customer);
            userProfile.setCreatedBy("SYSTEM");
            userProfile.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            userProfile = userProfileRepository.save(userProfile);
        }

        // Step 3: Check if Address exists for this profile
        List<Address> existingAddresses = addressRepository.findByUserProfile(userProfile);

        if (existingAddresses.isEmpty()) {
            Address address = new Address();
            address.setCustomerId(customer.getCustomerId());
            address.setUserProfile(userProfile);
            address.setAddressLine1(orderRequest.getProfile().getAddressLine1());
            address.setAddressLine2(orderRequest.getProfile().getAddressLine2());
            address.setLandmark(orderRequest.getProfile().getLandmark());
            address.setArea(orderRequest.getProfile().getArea());
            address.setCity(orderRequest.getProfile().getCity());
            address.setState(orderRequest.getProfile().getState());
            address.setCountry(orderRequest.getProfile().getCountry());
            address.setRegion(orderRequest.getProfile().getRegion());
            address.setPinCode(orderRequest.getProfile().getPostCode());
            address.setCreatedBy("SYSTEM");
            address.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            addressRepository.save(address);
        }

        // Step 4: Save the Order
        Orders order = new Orders();
        order.setRazorpayOrderId(razorpayOrderId);
        order.setUserId(orderRequest.getUserId());
        order.setAmount(orderRequest.getAmount());
        order.setEmail(orderRequest.getEmail());
        order.setMobileNumber(orderRequest.getMobileNumber());
        order.setCustomerName(username);
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

    @Transactional
    public OrderDTO getOrderById(Long orderId){
        Optional<Orders> optionalOrders = orderRepository.findById(orderId);
        if(optionalOrders.isPresent()){
            Orders orders = optionalOrders.get();
            return OrderMapper.convertToDTO(orders);
        }else {
            return null;
        }
    }

    @Transactional
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Orders> orders = orderRepository.findOrdersByUserId(userId);
        return orders.stream()
                .map(order -> {
                    OrderDTO dto = new OrderDTO();
                    dto.setOrderId(order.getOrderId());
                    dto.setOrderStatus(order.getOrderStatus());
                    dto.setUsername(order.getCustomerName());
                    dto.setEmail(order.getEmail());
                    dto.setAmount(String.valueOf(order.getAmount()));
                    dto.setMobileNumber(order.getMobileNumber());
                    dto.setRazorpayOrderId(order.getRazorpayOrderId());
                    // set other fields as needed
                    return dto;
                })
                .collect(Collectors.toList());
    }
    }
