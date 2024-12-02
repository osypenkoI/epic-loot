package com.e_commerce.epic_loot.api.controller;

import com.e_commerce.epic_loot.api.DTO.Page.ProductCardDTO;
import com.e_commerce.epic_loot.api.DTO.Page.ProductGroupDTO;
import com.e_commerce.epic_loot.api.DTO.Product.CriteriaDTO;
import com.e_commerce.epic_loot.api.DTO.Product.ProductDetailsDTO;
import com.e_commerce.epic_loot.api.DTO.Product.ReviewResponseDTO;
import com.e_commerce.epic_loot.api.security.JwtUtil;
import com.e_commerce.epic_loot.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {


    private final ProductService productService;

    private JwtUtil jwtUtil;

    public ProductController(ProductService productService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/public/product/viewProductDetails/{id}")
    public ResponseEntity<ProductDetailsDTO> viewProductDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductDetails(id));
    }

    @PostMapping("/public/product/productGroup")
    public ResponseEntity<ProductGroupDTO> viewProductGroup(
            @RequestBody CriteriaDTO criteriaDTO
            ) {
        ProductGroupDTO products = productService.getProductsByCriteria(criteriaDTO);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/public/product/create")
    public ResponseEntity<?> createProduct(
            @RequestBody ProductDetailsDTO productDetailsDTO
    ){
        productService.productCreate(productDetailsDTO);
        return ResponseEntity.ok()
                .body("Створено");
    }

    @GetMapping("/public/product/search")
    public List<ProductCardDTO> search(@RequestParam String query) {
        return productService.searchProducts(query);
    }

    @GetMapping("/public/product/review/{productId}")
    public List<ReviewResponseDTO> getReviews(@PathVariable Integer productId, HttpServletRequest request){

        return productService.getAllReviews(productId);
    }


}
