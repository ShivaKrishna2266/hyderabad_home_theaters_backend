package com.hyderabad_home_theaters.controller;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/data")
public class DataLoaderController {

   @Autowired
    private BrandService brandService;

    @GetMapping("/getAllBrands")
    private ResponseEntity<ApiResponse<List<BrandDTO>>> getAllBrands(){
        ApiResponse<List<BrandDTO>> response = new ApiResponse<>();
        List<BrandDTO> brandDTOS = brandService.getAllBrands();
        if(brandDTOS != null){
            response.setStatus(200);
            response.setMessage("Fetch All Brands successfully");
            response.setData(brandDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("failed to Fetch Brands");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
