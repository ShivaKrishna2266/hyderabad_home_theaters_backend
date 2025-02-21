package com.hyderabad_home_theaters.controller;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
