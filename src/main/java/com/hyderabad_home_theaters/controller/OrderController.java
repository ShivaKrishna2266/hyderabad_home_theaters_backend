package com.hyderabad_home_theaters.controller;

import com.hyderabad_home_theaters.DTOs.ApiResponse1;
import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.DTOs.OrderDTO;
import com.hyderabad_home_theaters.DTOs.payment.OrderRequest;
import com.hyderabad_home_theaters.DTOs.payment.OrderResponse;
import com.hyderabad_home_theaters.DTOs.payment.PaymentResponse;
import com.hyderabad_home_theaters.config.RazorPayClientConfig;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.services.OrderService;
import com.hyderabad_home_theaters.services.UserProfileService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private RazorpayClient client;

    private RazorPayClientConfig razorPayClientConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    public OrderController(RazorPayClientConfig razorpayClientConfig) throws RazorpayException {
        this.razorPayClientConfig = razorpayClientConfig;
        this.client = new RazorpayClient(razorpayClientConfig.getKey(), razorpayClientConfig.getSecret());
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse razorPay = null;
        try {

            userProfileService.updateProfile(orderRequest);
            String amountInPaise = convertRupeeToPaise(orderRequest.getAmount());
            Order order = createRazorPayOrder(amountInPaise, orderRequest);
            razorPay = getOrderResponse((String) order.get("id"), amountInPaise);
            // Save order in the database
            orderService.saveOrder(razorPay.getRazorpayOrderId(), orderRequest);
        } catch (RazorpayException e) {
            log.error("Exception while create payment order", e);
            return new ResponseEntity<>(new ApiResponse1(false, "Error while create payment order: " + e.getMessage()), HttpStatus.EXPECTATION_FAILED);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(razorPay);
    }


    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody PaymentResponse paymentResponse) {
        log.info("Received updateOrder request with orderId: {}, paymentId: {}, signature: {}",
                paymentResponse.getRazorpayOrderId(),
                paymentResponse.getRazorpayPaymentId(),
                paymentResponse.getRazorpaySignature());

        String errorMsg = orderService.validateAndUpdateOrder(
                paymentResponse.getRazorpayOrderId(),
                paymentResponse.getRazorpayPaymentId(),
                paymentResponse.getRazorpaySignature(),
                razorPayClientConfig.getSecret()
        );

        if (errorMsg != null) {
            log.warn("Payment update failed: {}", errorMsg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse1(false, errorMsg));
        }

        log.info("Payment update successful for Razorpay Order ID: {}", paymentResponse.getRazorpayOrderId());
        return ResponseEntity.ok(new ApiResponse1(true, paymentResponse.getRazorpayPaymentId()));
    }

    private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
        OrderResponse razorPay = new OrderResponse();
        razorPay.setApplicationFee(amountInPaise);
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(razorPayClientConfig.getKey());
        return razorPay;
    }

    private com.razorpay.Order createRazorPayOrder(String amount, OrderRequest orderRequest) throws RazorpayException, JSONException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "TXN_"+generateRandomNumber());
        return client.Orders.create(options);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        ApiResponse<List<OrderDTO>> response = new ApiResponse<>();
        List<OrderDTO> orderDTOS = orderService.getAllOrders();

        if (orderDTOS != null) {
            response.setStatus(200);
            response.setMessage("Fetched all Orders records successfully");
            response.setData(orderDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to fetch Orders Data");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderStatus}")
    public OrderDTO getOrderByStatus(@PathVariable String orderStatus, @RequestBody OrderDTO orderDTO) {
        orderDTO.setOrderStatus(orderStatus); // optionally set in DTO
        return orderService.getOrderByStatus(orderDTO);
    }
}