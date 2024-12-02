package com.e_commerce.epic_loot.service;

import com.e_commerce.epic_loot.api.DTO.Product.CategoryDTO;
import com.e_commerce.epic_loot.api.DTO.Product.FeatureDTO;
import com.e_commerce.epic_loot.api.DTO.Product.SubCategoryDTO;
import com.e_commerce.epic_loot.model.Category;
import com.e_commerce.epic_loot.model.Feature;
import com.e_commerce.epic_loot.model.SubCategory;
import com.e_commerce.epic_loot.model.repository.CategoryRepository;
import com.e_commerce.epic_loot.model.repository.FeatureRepository;
import com.e_commerce.epic_loot.model.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final FeatureRepository featureRepository;

    public CategoryService(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository,
                           FeatureRepository featureRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.featureRepository = featureRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        // Извлекаем все категории из базы данных
        List<Category> categories = categoryRepository.findAll();
        categories = categoryRepository.findAll();

        // Преобразуем каждую категорию в DTO
        return categories.stream()
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getTitle(),
                        category.getSubCategories().stream()
                                .map(subCategory -> new SubCategoryDTO(
                                        subCategory.getId(),
                                        subCategory.getTitle(),
                                        subCategory.getCategory().getId()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setTitle(categoryDTO.getTitle());
        category.setBannerUrl(categoryDTO.getBannerUrl());
        categoryRepository.save(category);
    }

    public void createSubCategory(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = new SubCategory();
        subCategory.setTitle(subCategoryDTO.getTitle());
        subCategory.setImageUrl(subCategoryDTO.getBannerUrl());
        subCategory.setCategory(categoryRepository.findById(subCategoryDTO.getCategoryId()).get());
        subCategoryRepository.save(subCategory);
    }

    public void createFeature(FeatureDTO featureDTO) {
        Feature feature = new Feature();
        feature.setTitle(featureDTO.getTitle());
        featureRepository.save(feature);

    }

    //TODO Вывод Категории с под категориями и их продуктами в формате до 4 товаров
    //TODO Вывод Подкатегории со всеми товарами в ней


}
