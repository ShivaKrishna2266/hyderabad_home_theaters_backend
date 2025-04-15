package com.hyderabad_home_theaters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.DTOs.ContactUsDTO;
import com.hyderabad_home_theaters.DTOs.CountryCodeDTO;
import com.hyderabad_home_theaters.DTOs.GeneralSettingsDTO;
import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.DTOs.QuestionsDTO;
import com.hyderabad_home_theaters.DTOs.ReviewDTO;
import com.hyderabad_home_theaters.DTOs.SubCategoryDTO;
import com.hyderabad_home_theaters.DTOs.TestimonialDTO;
import com.hyderabad_home_theaters.entity.ApiResponse;
import com.hyderabad_home_theaters.exception.ResourceNotFoundException;
import com.hyderabad_home_theaters.services.BrandService;
import com.hyderabad_home_theaters.services.CategoryService;
import com.hyderabad_home_theaters.services.ContactUsService;
import com.hyderabad_home_theaters.services.CountryCodeService;
import com.hyderabad_home_theaters.services.GeneralSettingService;
import com.hyderabad_home_theaters.services.ProductService;
import com.hyderabad_home_theaters.services.QuestionsServices;
import com.hyderabad_home_theaters.services.ReviewServices;
import com.hyderabad_home_theaters.services.SubCategoryService;
import com.hyderabad_home_theaters.services.TestimonialService;
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
import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/data")
public class DataLoaderController {

   @Autowired
    private BrandService brandService;

   @Autowired
   private CategoryService categoryService;

   @Autowired
   private ProductService productService;

   @Autowired
   private SubCategoryService subCategoryService;

   @Autowired
    private ContactUsService contactUsService;

   @Autowired
   private CountryCodeService countryCodeService;

   @Autowired
   private GeneralSettingService generalSettingService;

   @Autowired
   private TestimonialService testimonialService;

   @Autowired
   private ReviewServices reviewServices;

   @Autowired
   private QuestionsServices questionsServices;


    @Value("${file.upload.review.dir}")
    private String uploadReviewDir;

    @Value("${file.upload.question.dir}")
    private String uploadQuestionDir;



   //========================BRANDS=======================//

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

    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Long categoryId){
        ApiResponse<CategoryDTO> response = new ApiResponse<>();
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
        if (categoryDTO != null){
            response.setStatus(200);
            response.setMessage("Fetch Brand By Id Data Successfully");
            response.setData(categoryDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to fetch CategoryById Data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/createCategory")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@RequestBody CategoryDTO categoryDTO){
        ApiResponse<CategoryDTO> response = new ApiResponse<>();
        CategoryDTO categoryDTO1 = categoryService.createCategory(categoryDTO);
        if(categoryDTO1 != null){
            response.setStatus(200);
            response.setMessage("Create Category is Successfully!");
            response.setData(categoryDTO1);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed to Create Category data");
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
        ApiResponse<CategoryDTO> response = new ApiResponse<>();
        CategoryDTO categoryDTO1 = categoryService.updateCategory(categoryId,categoryDTO);
        if (categoryDTO1 != null){
            response.setStatus(200);
            response.setMessage("Updated Category Successfully");
            response.setData(categoryDTO1);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatus(500);
            response.setMessage("Failed Update category");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        ApiResponse<Void> response = new ApiResponse<>();
        categoryService.deleteCategoryById(categoryId);
        if (categoryId != null) {
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


    @GetMapping("/getProductByCategory/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByCategory(@PathVariable Long categoryId){
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        List<ProductDTO> productDTOS = productService.getProductsByCategory(categoryId);
        if (productDTOS != null){
            response.setStatus(200);
            response.setMessage("Successfully Fetched  a Product By Category!");
            response.setData(productDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to Product By Category!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //===================Product============================//


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
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO){
        ApiResponse<ProductDTO> response = new ApiResponse<>();
        ProductDTO productDTO1 = productService.createProduct(productDTO);
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


    //==========================SUBCATEGORY==============================//

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


    //====================CONTACT US==========================================//


    @PostMapping("/addContactUs")
    public ResponseEntity<ApiResponse<ContactUsDTO>> addContactUs(@RequestBody ContactUsDTO contactUsDTO) {
        ApiResponse<ContactUsDTO> response = new ApiResponse<>();
        ContactUsDTO contactUsDTOs = contactUsService.createContactUs(contactUsDTO);
        if (contactUsDTOs != null) {
            response.setStatus(200);
            response.setMessage("Created contact successfully!");
            response.setData(contactUsDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to create contact!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //===========================Country Code===============================//

    @GetMapping("/getAllCountryCodes")
    public ResponseEntity<ApiResponse<List<CountryCodeDTO>>> getAllCountryCodes() {
        ApiResponse<List<CountryCodeDTO>> response = new ApiResponse<>();
        List<CountryCodeDTO> countryCodeDTO = countryCodeService.getAllCountryCodes();
        if (countryCodeDTO != null) {
            response.setStatus(200);
            response.setMessage("fetched successfully!");
            response.setData(countryCodeDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("no records were found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //===================General Settings =============================//

    @GetMapping("/getAllGeneralSettings")
    public ResponseEntity<ApiResponse<List<GeneralSettingsDTO>>> getAllGeneralSettings() {
        ApiResponse<List<GeneralSettingsDTO>> response = new ApiResponse<>();
        List<GeneralSettingsDTO> generalSettingsDTOS = generalSettingService.getAllGeneralSettings();
        if (generalSettingsDTOS != null) {
            response.setStatus(200);
            response.setMessage("GeneralSettings retrieved successfully");
            response.setData(generalSettingsDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {

            response.setStatus(500);
            response.setMessage("GeneralSettings retrieved failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //================================TESTIMONIAL=====================================//


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

    @PostMapping("/createReview")
    public ResponseEntity<ApiResponse<ReviewDTO>> createReview(
            @RequestPart("reviewDTO") String reviewDTO,
            @RequestPart("reviewImageFile") MultipartFile reviewImageFile) {
        ApiResponse<ReviewDTO> response = new ApiResponse<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ReviewDTO reviewDtoObj = objectMapper.readValue(reviewDTO, ReviewDTO.class);
            String fileName = reviewImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadReviewDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(reviewImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            reviewDtoObj.setImage(fileName);
            ReviewDTO savedReview = reviewServices.createReview(reviewDtoObj);
            if (savedReview != null) {
                response.setStatus(200);
                response.setMessage("Review created successfully!");
                response.setData(savedReview);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(500);
                response.setMessage("Failed to create review!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Error processing review creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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

    @PostMapping("/createQuestion")
    public ResponseEntity<ApiResponse<QuestionsDTO>> createQuestion(@RequestPart("questionDTO") String questionsDTO,
                                                                    @RequestPart("questionImageFile") MultipartFile questionImageFile) {
        ApiResponse<QuestionsDTO> response = new ApiResponse<>();

        try {
            // Convert JSON string to DTO object
            ObjectMapper objectMapper = new ObjectMapper();
            QuestionsDTO questionsDTODtoObj = objectMapper.readValue(questionsDTO, QuestionsDTO.class);

            // Save image file
            String fileName = questionImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadQuestionDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.copy(questionImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set image filename to DTO
            questionsDTODtoObj.setImage(fileName);

            // Save to DB using service
        QuestionsDTO savedQuestion = questionsServices.createQuestion(questionsDTODtoObj);
        if (savedQuestion != null) {
            response.setStatus(200);
            response.setMessage("Get Question By Id successfully!");
            response.setData(savedQuestion);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessage("Failed to get Question By Id!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Error processing testimonial creation: " + e.getMessage());
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
