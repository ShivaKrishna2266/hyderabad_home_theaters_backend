package com.hyderabad_home_theaters.controller;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.exception.ResourceNotFoundException;
import com.hyderabad_home_theaters.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
public class AdminController {


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

    @GetMapping("/getBrandById/{brandId}")
    public ResponseEntity<ApiResponse<BrandDTO>> getBrandById(@PathVariable Long brandId ){
        ApiResponse<BrandDTO> response = new ApiResponse<>();
        BrandDTO brandDTO = brandService.getBrandById(brandId);
        if(brandDTO != null){
            response.setStatus(200);
            response.setMessage("Fetch Brand By Id Data Successfully");
            response.setData(brandDTO);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to fetch BrandById Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/createBrand")
    public ResponseEntity<ApiResponse<BrandDTO>> createBrand (@RequestBody BrandDTO brandDTO){
        ApiResponse<BrandDTO> response = new ApiResponse<>();
        BrandDTO brandDTO1 = brandService.createBrand(brandDTO);
        if(brandDTO1 != null){
            response.setStatus(200);
            response.setMessage("Create Brand Successfully");
            response.setData(brandDTO1);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to Create the Brand");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateBrand/{brandId}")
    public ResponseEntity<ApiResponse<BrandDTO>> updateBrandById(@PathVariable Long brandId, @RequestBody BrandDTO brandDTO) {
        ApiResponse<BrandDTO> response = new ApiResponse<>();

        try {
            BrandDTO updatedBrand = brandService.updateBrand(brandId, brandDTO);
            response.setStatus(200);
            response.setMessage("Updated Brand Successfully");
            response.setData(updatedBrand);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to update Brand: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteBrand/{brandId}")
    public ResponseEntity<ApiResponse<Void>> deleteBrandById(@PathVariable Long brandId) {
        ApiResponse<Void> response = new ApiResponse<>();
            brandService.deleteBrandById(brandId);
            if (brandId != null) {
                response.setStatus(200);
                response.setMessage("Successfully deleted a Brand!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to delete a Brand!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }