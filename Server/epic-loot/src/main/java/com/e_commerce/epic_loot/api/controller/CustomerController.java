package com.e_commerce.epic_loot.api.controller;


import com.e_commerce.epic_loot.api.DTO.Page.ProductListDTO;
import com.e_commerce.epic_loot.api.DTO.Product.ReviewDTO;
import com.e_commerce.epic_loot.api.security.JwtUtil;
import com.e_commerce.epic_loot.model.Product;
import com.e_commerce.epic_loot.service.CustomerService;
import com.e_commerce.epic_loot.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final JwtUtil jwtUtil;

    public CustomerController(CustomerService customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/view-all")
    public ResponseEntity<List<Product>> viewAllCustomers() {
        return ResponseEntity.ok(customerService.getCustomerRepository());
    }

    //TODO GET-METHOD WishList

    @PostMapping("/wishlist")
    public ResponseEntity<Void> addProductToWishlist(@RequestParam Integer productId, HttpServletRequest request) {
        // Получаем токен из запроса
        String token = jwtUtil.getTokenFromRequest(request);

        // Извлекаем customerId из токена
        Integer customerId = jwtUtil.extractCustomerId(token, jwtUtil.getSECRET_KEY());


        // Добавляем продукт в вишлист пользователя
        customerService.addProductToWishList(customerId, productId);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/wishlist/check/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(@PathVariable Integer productId, HttpServletRequest request) {
        String token = jwtUtil.getTokenFromRequest(request);

        // Извлекаем customerId из токена
        Integer customerId = jwtUtil.extractCustomerId(token, jwtUtil.getSECRET_KEY());

        return ResponseEntity.ok( customerService.isProductInWishlist(productId,customerId));
    }

    @DeleteMapping("/wishlist")
    public ResponseEntity<Void> deleteProductFromWishlist(@RequestParam Integer productId, HttpServletRequest request) {
        // Получаем токен из запроса
        String token = jwtUtil.getTokenFromRequest(request);

        // Извлекаем customerId из токена
        Integer customerId = jwtUtil.extractCustomerId(token, jwtUtil.getSECRET_KEY());


        // Добавляем продукт в вишлист пользователя
        customerService.deleteProductFromWishList(customerId, productId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request) {
        String token = jwtUtil.getTokenFromRequest(request);

        // Извлекаем customerId из токена
        Integer customerId = jwtUtil.extractCustomerId(token, jwtUtil.getSECRET_KEY());
        customerService.createReview(reviewDTO, customerId);
        return ResponseEntity.ok().build();
    }



    //TODO GET-METHOD PURCHASEDGAMES
}
