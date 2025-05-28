package com.hyderabad_home_theaters.controller;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.DTOs.OrderDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.services.BrandService;
import com.hyderabad_home_theaters.services.OrderService;
import com.hyderabad_home_theaters.services.UserDetailsService;
import com.hyderabad_home_theaters.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserProfileService userProfileService;



    @GetMapping("/getAllBrands")
    public ResponseEntity<ApiResponse<List<BrandDTO>>> getAllBrands() {
        ApiResponse<List<BrandDTO>> response = new ApiResponse<>();
        List<BrandDTO> brandDTO = brandService.getAllBrands();

        if (brandDTO != null) {
            response.setStatus(200);
            response.setMessage("Fetched all brand records successfully");
            response.setData(brandDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to fetch Brand Data");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createBrand")
    public ResponseEntity<ApiResponse<BrandDTO>> createBrand(@RequestBody BrandDTO brandDTO){
        ApiResponse<BrandDTO> response = new ApiResponse<>();
        BrandDTO brandDTO1 = brandService.createBrand(brandDTO);
        if (brandDTO1 != null){
            response.setStatus(200);
            response.setMessage("Create Brand Successfully");
            response.setData(brandDTO1);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to Create Brand");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long userId){
        ApiResponse<UserDTO>  response = new ApiResponse<>();
        UserDTO userDTOs = userDetailsService.getUserById(userId);
        if (userDTOs != null){
            response.setStatus(200);
            response.setMessage("Fetch User By Id Data Successfully");
            response.setData(userDTOs);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed To Fetch User By Id Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getUserByFirstName/{username}")
    public ResponseEntity<ApiResponse<ProfileDTO>> getUserByFirstName(@PathVariable String username){
        ApiResponse<ProfileDTO>  response = new ApiResponse<>();
        ProfileDTO userDTOs = userDetailsService.getProfileUsername(username);
        if (userDTOs != null){
            response.setStatus(200);
            response.setMessage("Fetch User By Id Data Successfully");
            response.setData(userDTOs);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed To Fetch User By Id Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long orderId){
        ApiResponse<OrderDTO>  response = new ApiResponse<>();
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        if (orderDTO != null){
            response.setStatus(200);
            response.setMessage("Fetch Order By Id Data Successfully");
            response.setData(orderDTO);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed To Fetch Order By Id Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getOrdersByUserId/{userId}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByUserId(@PathVariable Long userId){
        ApiResponse<List<OrderDTO>>  response = new ApiResponse<>();
        List<OrderDTO> orderDTO = orderService.getOrdersByUserId(userId);
        if (orderDTO != null){
            response.setStatus(200);
            response.setMessage("Fetch Order By Id  User Data Successfully");
            response.setData(orderDTO);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed To Fetch Order By Id SuerData");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
