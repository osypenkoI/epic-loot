package com.e_commerce.epic_loot.api.controller;


import com.e_commerce.epic_loot.api.DTO.Product.CategoryDTO;
import com.e_commerce.epic_loot.api.DTO.Product.FeatureDTO;
import com.e_commerce.epic_loot.api.DTO.Product.SubCategoryDTO;
import com.e_commerce.epic_loot.model.Category;
import com.e_commerce.epic_loot.model.Product;
import com.e_commerce.epic_loot.service.CategoryService;
import com.e_commerce.epic_loot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categoryList")
    public ResponseEntity<List<CategoryDTO>> viewCategoryList() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok()
                .body("Category created");
    }

    @PostMapping("/createSubCategory")
    public ResponseEntity<?> createSubCategory(@RequestBody SubCategoryDTO subcategoryDTO) {
        categoryService.createSubCategory(subcategoryDTO);
     return ResponseEntity.ok()
             .body("SubCategory created");
    }

    @PostMapping("/createFeature")
    public ResponseEntity<?> createFeature(@RequestBody FeatureDTO featureDTO) {
        categoryService.createFeature(featureDTO);
        return ResponseEntity.ok()
                .body("Feature created");
    }


}
