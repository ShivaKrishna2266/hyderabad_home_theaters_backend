package com.hyderabad_home_theaters.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.DTOs.QuestionsDTO;
import com.hyderabad_home_theaters.DTOs.ReviewDTO;
import com.hyderabad_home_theaters.DTOs.SubCategoryDTO;
import com.hyderabad_home_theaters.DTOs.TestimonialDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.entity.MessageTemplate;
import com.hyderabad_home_theaters.exception.ResourceNotFoundException;
import com.hyderabad_home_theaters.services.BrandService;
import com.hyderabad_home_theaters.services.CategoryService;
import com.hyderabad_home_theaters.services.ProductService;
import com.hyderabad_home_theaters.services.QuestionsServices;
import com.hyderabad_home_theaters.services.ReviewServices;
import com.hyderabad_home_theaters.services.SubCategoryService;
import com.hyderabad_home_theaters.services.TestimonialService;
import com.hyderabad_home_theaters.services.WatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.io.File;
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

    @Autowired
    private ProductService productService;
    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private TestimonialService testimonialService;


    @Autowired
    private WatiService watiService;

    @Autowired
    private ReviewServices reviewServices;

    @Autowired
    private QuestionsServices questionsServices;


    @Value("${file.upload.brand.dir}")
    private String uploadBrandDir;

    @Value("${file.upload.category.dir}")
    private String uploadCategoryDir;

    @Value("${file.upload.product.dir}")
    private String uploadProductDir;

    @Value("${file.upload.testimonial.dir}")
    private String uploadTestimonialDir;

    @GetMapping("/getAllBrands")
    private ResponseEntity<ApiResponse<List<BrandDTO>>> getAllBrands() {
        ApiResponse<List<BrandDTO>> response = new ApiResponse<>();
        List<BrandDTO> brandDTOS = brandService.getAllBrands();
        if (brandDTOS != null) {
            response.setStatus(200);
            response.setMessage("Fetch All Brands successfully");
            response.setData(brandDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("failed to Fetch Brands");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBrandById/{brandId}")
    public ResponseEntity<ApiResponse<BrandDTO>> getBrandById(@PathVariable Long brandId) {
        ApiResponse<BrandDTO> response = new ApiResponse<>();
        BrandDTO brandDTO = brandService.getBrandById(brandId);
        if (brandDTO != null) {
            response.setStatus(200);
            response.setMessage("Fetch Brand By Id Data Successfully");
            response.setData(brandDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to fetch BrandById Data");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        ApiResponse<List<CategoryDTO>> response = new ApiResponse<>();
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        if (categoryDTOS != null) {
            response.setStatus(200);
            response.setMessage("Fetched All Categories Successfully");
            response.setData(categoryDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("failed to Fetch Categories");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createCategory")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@RequestPart("categoryDTO") String categoryDTO,
                                                                   @RequestPart("categoryImageFile") MultipartFile categoryImageFile) {
        ApiResponse<CategoryDTO> response = new ApiResponse<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CategoryDTO categoryIamgeDtoObj = objectMapper.readValue(categoryDTO, CategoryDTO.class);
            String fileName = categoryImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadCategoryDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(categoryImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            categoryIamgeDtoObj.setImageUrl(fileName);

            CategoryDTO savedCategory = categoryService.createCategory(categoryIamgeDtoObj);

            if (savedCategory != null) {
                response.setStatus(200);
                response.setMessage("Category added successfully");
                response.setData(savedCategory);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to add category");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Error processing brand creation: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCategoryById/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategoryById(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {

        ApiResponse<CategoryDTO> response = new ApiResponse<>();

        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);

            if (updatedCategory != null) {
                response.setStatus(200);
                response.setMessage("Category updated successfully");
                response.setData(updatedCategory);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(404);
                response.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error updating category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    //========================PRODUCT=========================================//




    @GetMapping("/getAllProducts")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts(){
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        List<ProductDTO> productDTOS = productService.getAllProducts();
        if (productDTOS != null){
            response.setStatus(200);
            response.setMessage("Fetch All Products Successfully");
            response.setData(productDTOS);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to fetch Get All Products");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long productId){
        ApiResponse<ProductDTO>  response = new ApiResponse<>();
        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO != null){
            response.setStatus(200);
            response.setMessage("Fetch Product By Id Data Successfully");
            response.setData(productDTO);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed To Fetch Product By Id Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct( @RequestPart("productDTO") String productDTO,
                                                                  @RequestPart("productImageFile") MultipartFile productImageFile) {

        ApiResponse<ProductDTO> response = new ApiResponse<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productImageDtoObj = objectMapper.readValue(productDTO, ProductDTO.class);

            // Extract filename
            String fileName = productImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadProductDir, fileName);

            // Ensure directory exists
            Files.createDirectories(filePath.getParent());

            // Save the file
            Files.copy(productImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            productImageDtoObj.setImageURL(fileName);
        ProductDTO productDTO1 = productService.createProduct(productImageDtoObj);
        if (productDTO1 != null){
            response.setStatus(200);
            response.setMessage("Create Product Successfully");
            response.setData(productDTO1);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to create Product");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Error processing brand creation: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@RequestBody ProductDTO productDTO,
                                                                 @PathVariable Long productId){
        ApiResponse<ProductDTO> response = new ApiResponse<>();
        ProductDTO productDTO1 = productService.updateProduct(productId,productDTO);
        if (productDTO1 != null){
            response.setStatus(200);
            response.setMessage("Update Product Successfully");
            response.setData(productDTO);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to Update Product");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //=======================Get Product By Brand ===========================//

    @GetMapping("/getProductsByBrand/{brandId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByBrand(@PathVariable Long brandId) {
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        List<ProductDTO> products = productService.getProductsByBrand(brandId);

        if (!products.isEmpty()) {
            response.setStatus(200);
            response.setMessage("Fetch data successfully");
            response.setData(products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(404);
            response.setMessage("No products found for the given brand");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        ApiResponse<Void> response = new ApiResponse<>();
        productService.deleteProductById(productId);
        if (productId != null) {
            response.setStatus(200);
            response.setMessage("Successfully deleted a Product!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to delete a Product!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //==========================SUB CATEGORY======================================//


    @GetMapping("/getAllSubCategories")
    public ResponseEntity<ApiResponse<List<SubCategoryDTO>>> getAllSubCategories(){
        ApiResponse<List<SubCategoryDTO>> response = new ApiResponse<>();
        List<SubCategoryDTO> subCategories = subCategoryService.getAllSubCategories();
        if (subCategories != null){
            response.setStatus(200);
            response.setMessage("Fetch All SubCategories Successfully");
            response.setData(subCategories);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to fetch Get All SubCategories");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSubCategoryById/{subCategoryId}")
    public ResponseEntity<ApiResponse<SubCategoryDTO>> getSubCategoryById(@PathVariable Long subCategoryId){
        ApiResponse<SubCategoryDTO>  response = new ApiResponse<>();
        SubCategoryDTO subcategoryById = subCategoryService.getSubcategoryById(subCategoryId);
        if (subcategoryById != null){
            response.setStatus(200);
            response.setMessage("Fetch SubCategory By Id Data Successfully");
            response.setData(subcategoryById);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed To Fetch SubCategory By Id Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createSubCategory")
    public ResponseEntity<ApiResponse<SubCategoryDTO>> createSubCategory(@RequestBody SubCategoryDTO subCategoryDTO){
        ApiResponse<SubCategoryDTO> response = new ApiResponse<>();
        SubCategoryDTO subCategoryDTO1 = subCategoryService.createSubCategory(subCategoryDTO);
        if (subCategoryDTO1 != null){
            response.setStatus(200);
            response.setMessage("Create SubCategory Successfully");
            response.setData(subCategoryDTO1);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to create SubCategory");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateSubCategory/{subCategoryId}")
    public ResponseEntity<ApiResponse<SubCategoryDTO>> updateSubCategory(@RequestBody SubCategoryDTO subCategoryDTO,
                                                                         @PathVariable Long subCategoryId){
        ApiResponse<SubCategoryDTO> response = new ApiResponse<>();
        SubCategoryDTO subCategoryDTO1 = subCategoryService.updateSubCategory(subCategoryId,subCategoryDTO);
        if (subCategoryDTO1 != null){
            response.setStatus(200);
            response.setMessage("Update SubCategory Successfully");
            response.setData(subCategoryDTO1);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to Update SubCategory");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteSubCategoryById/{subCategoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteSubCategoryById(@PathVariable Long subCategoryId) {
        ApiResponse<Void> response = new ApiResponse<>();
        subCategoryService.deleteSubCategoryById(subCategoryId);
        if (subCategoryId != null) {
            response.setStatus(200);
            response.setMessage("Successfully deleted a SubCategory!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to delete a SubCategory!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //====================Get SubCategory By Category===============================//

    @GetMapping("/getSubCategoryByCategory/{categoryId}")
    public ResponseEntity<ApiResponse<List<SubCategoryDTO>>> getSubCategoryByCategoryId(@PathVariable Long categoryId){
        ApiResponse<List<SubCategoryDTO>> response = new ApiResponse<>();
        List<SubCategoryDTO> subCategoryDTOS = subCategoryService.getSubCategoryByCategory(categoryId);
        if (subCategoryDTOS != null){
            response.setStatus(200);
            response.setMessage("Successfully Fetched  a SubCategory By Category!");
            response.setData(subCategoryDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to SubCategory By Category!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // ==================Testimonial APis======================//

    @GetMapping("/getAllTestimonials")
    public ResponseEntity<ApiResponse<List<TestimonialDTO>>> getAllTestimonials() {
        ApiResponse<List<TestimonialDTO>> response = new ApiResponse<>();
        List<TestimonialDTO> testimonialDTOS = testimonialService.getAllTestimonials();
        if (testimonialDTOS != null) {
            response.setStatus(200);
            response.setMessage("Created testimonial modules successfully!");
            response.setData(testimonialDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to create  course modules!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addTestimonials")
    public ResponseEntity<ApiResponse<TestimonialDTO>> addTestimonials(
            @RequestPart("testimonialDTO") String testimonialDTO,
            @RequestPart("testimonialImageFile") MultipartFile testimonialImageFile) {

        ApiResponse<TestimonialDTO> response = new ApiResponse<>();

        try {
            // Convert JSON string to DTO object
            ObjectMapper objectMapper = new ObjectMapper();
            TestimonialDTO testimonialDtoObj = objectMapper.readValue(testimonialDTO, TestimonialDTO.class);

            // Save image file
            String fileName = testimonialImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadTestimonialDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.copy(testimonialImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set image filename to DTO
            testimonialDtoObj.setImage(fileName);

            // Save to DB using service
            TestimonialDTO savedTestimonial = testimonialService.createTestimonial(testimonialDtoObj);

            if (savedTestimonial != null) {
                response.setStatus(200);
                response.setMessage("Successfully added testimonial.");
                response.setData(savedTestimonial);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to create testimonial.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Error processing testimonial creation: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateTestimonial/{testimonialId}")
    public ResponseEntity<ApiResponse<TestimonialDTO>> updateTestimonial(@RequestPart("testimonialDTO") String testimonialDTO,
                                                                         @RequestPart("testimonialImageFile") MultipartFile testimonialImageFile,
                                                                         @PathVariable Long testimonialId) {
        ApiResponse<TestimonialDTO> response = new ApiResponse<>();
        try {
            // Convert JSON string to DTO object
            ObjectMapper objectMapper = new ObjectMapper();
            TestimonialDTO testimonialDtoObj = objectMapper.readValue(testimonialDTO, TestimonialDTO.class);

            // Save image file
            String fileName = testimonialImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadTestimonialDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.copy(testimonialImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set image filename to DTO
            testimonialDtoObj.setImage(fileName);

            // Save to DB using service
            TestimonialDTO updatedTestimonial = testimonialService.updateTestimonial(testimonialId, testimonialDtoObj);
            if (updatedTestimonial != null) {
                response.setStatus(200);
                response.setMessage("Testimonial updated successfully.");
                response.setData(updatedTestimonial);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(404);
                response.setMessage("Testimonial not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to update testimonial: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/sendWatiSMSForAllUsers/{templateName}/{role}")
    public ResponseEntity<ApiResponse> sendWatiSMSForAllUsers(@PathVariable String templateName,
                                                                 @PathVariable String role) throws Exception {
        ApiResponse response = new ApiResponse<>();
        watiService.sendWatiSMSByRole(templateName, role);
        try {
            if (response != null) {
                response.setStatus(200);
                response.setMessage("sendWatiSMSByRole messages sent successfully.");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to send sendWatiSMSByRole.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to send WhatsAppDailyLeads messages.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTemplateMessages")
    public ResponseEntity<ApiResponse<List<MessageTemplate>>> getTemplateMessages() {
        ApiResponse<List<MessageTemplate>> response = new ApiResponse<>();
        List<MessageTemplate> messageTemplates = watiService.getTemplateMessages();
        try {
            if (messageTemplates != null) {
                response.setStatus(200);
                response.setMessage("fetched all MessageTemplates Successfully");
                response.setData(messageTemplates);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to fetched all MessageTemplates");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to send WhatsAppDailyLeads messages.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //========================REVIEW==========================//

    @GetMapping("/getAllReviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getAllReviews() {
        ApiResponse<List<ReviewDTO>> response = new ApiResponse<>();
        List<ReviewDTO> reviewDTOS = reviewServices.getAllReviews();
        if (reviewDTOS != null) {
            response.setStatus(200);
            response.setMessage("Created reviews successfully!");
            response.setData(reviewDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to create  reviews!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getProductReviews/{productId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getProductReviews(@PathVariable Long productId) {
        ApiResponse<List<ReviewDTO>> response = new ApiResponse<>();

        try {
            List<ReviewDTO> reviews = reviewServices.getReviewByProductId(productId);
            response.setStatus(200);
            response.setMessage("Get Product review successfully!");
            response.setData(reviews);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to get Product reviews!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //========================QUESTIONS==========================//

    @GetMapping("/getAllQuestions")
    public ResponseEntity<ApiResponse<List<QuestionsDTO>>> getAllQuestions() {
        ApiResponse<List<QuestionsDTO>> response = new ApiResponse<>();
        List<QuestionsDTO> questionsDTOS = questionsServices.getAllQuestions();
        if (questionsDTOS != null) {
            response.setStatus(200);
            response.setMessage("Created Question successfully!");
            response.setData(questionsDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to create  Question!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getQuestionById/{questionId}")
    public ResponseEntity<ApiResponse<QuestionsDTO>> getQuestionById(@PathVariable Long questionId) {
        ApiResponse<QuestionsDTO> response = new ApiResponse<>();
        QuestionsDTO questionsDTOS = questionsServices.getQuestionById(questionId);

        if (questionsDTOS != null) {
            response.setStatus(200);
            response.setMessage("Get Question By Id successfully!");
            response.setData(questionsDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to get Question By Id!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateQuestionById/{questionId}")
    public ResponseEntity<ApiResponse<QuestionsDTO>> updateQuestion(@PathVariable Long questionId,
                                                                    @RequestBody QuestionsDTO questionsDTO) {
        ApiResponse<QuestionsDTO> response = new ApiResponse<>();
        QuestionsDTO updatedQuestion = questionsServices.updateQuestion(questionId, questionsDTO);
        if (updatedQuestion != null) {
            response.setStatus(200);
            response.setMessage("Question updated successfully!");
            response.setData(updatedQuestion);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to update question");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProductQuestion/{productId}/question")
    public ResponseEntity<ApiResponse<List<QuestionsDTO>>> getProductQuestion(@PathVariable Long productId) {
        ApiResponse<List<QuestionsDTO>> response = new ApiResponse<>();

        try {
            List<QuestionsDTO> questionsDTOS = questionsServices.getQuestionByProductId(productId);
            response.setStatus(200);
            response.setMessage("Get Product question successfully!");
            response.setData(questionsDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to get Product question!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}