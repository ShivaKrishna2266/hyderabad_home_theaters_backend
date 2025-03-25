package com.hyderabad_home_theaters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.exception.ResourceNotFoundException;
import com.hyderabad_home_theaters.services.BrandService;
import com.hyderabad_home_theaters.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;





    @Value("${file.upload.brand.dir}")
    private String uploadBrandDir;

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
    public ResponseEntity<ApiResponse<BrandDTO>> createBrand(
            @RequestPart("brandDTO") String brandDTO,
            @RequestPart("brandImageFile") MultipartFile brandImageFile) {

        ApiResponse<BrandDTO> response = new ApiResponse<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BrandDTO brandImageDtoObj = objectMapper.readValue(brandDTO, BrandDTO.class);

            // Extract filename
            String fileName = brandImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadBrandDir, fileName);

            // Ensure directory exists
            Files.createDirectories(filePath.getParent());

            // Save the file
            Files.copy(brandImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            brandImageDtoObj.setImageURL(fileName);

            // Call service to create brand
            BrandDTO createdBrand = brandService.createBrand(brandImageDtoObj);
            if (createdBrand != null) {
                response.setStatus(200);
                response.setMessage("Brand created successfully");
                response.setData(createdBrand);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to create the brand");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Error processing brand creation: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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


    @GetMapping("/updateBrandStatus/{brandId}/{status}")
    public ResponseEntity<ApiResponse<BrandDTO>> updateBrandStatus(@PathVariable Long brandId,
                                                                               @PathVariable String status) {
        ApiResponse<BrandDTO> response = new ApiResponse<>();
        BrandDTO brandDTOs = brandService.updateBrandStatusById(brandId, status);
        if (brandDTOs != null) {
            response.setStatus(200);
            response.setMessage("Updated a Brand successfully!");
            response.setData(brandDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to fetch");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //========================CATEGORY=======================//

    @GetMapping("/getAllCategories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories(){
        ApiResponse<List<CategoryDTO>> response = new ApiResponse<>();
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        if (categoryDTOS != null){
            response.setStatus(200);
            response.setMessage("Fetched All Categories Successfully");
            response.setData(categoryDTOS);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("failed to Fetch Categories");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    }