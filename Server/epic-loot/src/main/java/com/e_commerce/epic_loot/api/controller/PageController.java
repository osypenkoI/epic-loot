package com.e_commerce.epic_loot.api.controller;

import com.e_commerce.epic_loot.api.DTO.Page.CategoryPageDTO;
import com.e_commerce.epic_loot.api.DTO.Page.HomePageDTO;
import com.e_commerce.epic_loot.api.DTO.Page.ProductListDTO;
import com.e_commerce.epic_loot.api.security.JwtUtil;
import com.e_commerce.epic_loot.service.PageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PageController {
    @Autowired
    private PageService pageService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/public/home/{id}")
    public ResponseEntity<HomePageDTO> getHomePage(@PathVariable Integer id) {
        HomePageDTO homePage = pageService.getHomePage(id);
        return ResponseEntity.ok(homePage);
    }

    @GetMapping("/public/category/{id}")
    public ResponseEntity<CategoryPageDTO> getCategoryPage(@PathVariable Integer id) {
        CategoryPageDTO categoryPage = pageService.getCategoryPage(id);
        return ResponseEntity.ok(categoryPage);
    }

    @GetMapping("/public/subcategory/{id}")
    public ResponseEntity<ProductListDTO> getSubcategoryPage(@PathVariable Integer id) {
        ProductListDTO subcategoryPage = pageService.getSubcategoryPage(id);
        return ResponseEntity.ok(subcategoryPage);
    }

    @GetMapping("/customer/wishlist")
    public ResponseEntity<ProductListDTO> getWishlist(HttpServletRequest request) {
        String token = jwtUtil.getTokenFromRequest(request);
        // Извлекаем customerId из токена
        Integer customerId = jwtUtil.extractCustomerId(token, jwtUtil.getSECRET_KEY());
        ProductListDTO wishlist = pageService.getWishlist(customerId);
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping("/customer/purchasedGames")
    public ResponseEntity<ProductListDTO> getPurchasedGames(HttpServletRequest request) {
        String token = jwtUtil.getTokenFromRequest(request);
        // Извлекаем customerId из токена
        Integer customerId = jwtUtil.extractCustomerId(token, jwtUtil.getSECRET_KEY());
        ProductListDTO wishlist = pageService.getPurchasedGames(customerId);
        return ResponseEntity.ok(wishlist);
    }
/*
    @GetMapping("/public/searchResult")
    public ResponseEntity<ProductListDTO> getSearchResults(@RequestParam String query) {
        //ProductListDTO results = pageService.getSearchResults(query);
        return ResponseEntity.ok(results);
    }*/
}
