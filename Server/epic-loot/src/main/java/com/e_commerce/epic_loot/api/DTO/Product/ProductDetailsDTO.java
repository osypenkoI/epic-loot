package com.e_commerce.epic_loot.api.DTO.Product;


import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDetailsDTO {

    private String title;

    private String description;

    private Integer price;

    private BigDecimal discount;

    private Integer discountPrice;

    private String mainPictureUrl;

    private List<String> otherPictureUrl = new ArrayList<>();

    private List<FeatureDTO> features = new ArrayList<>();

    private List<CategoryDTO> categories = new ArrayList<>();

    private RequirementsDTO minimumRequirements;

    private RequirementsDTO recommendedRequirements;

    public Integer calculateDiscountPrice(Integer price, BigDecimal discountPercent) {
        if (discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) <= 0) {
            return price; // Если скидка 0% или отрицательная, возвращаем оригинальную цену
        }

        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountPercent.divide(BigDecimal.valueOf(100))); // Преобразуем процент в множитель
        BigDecimal discountedPrice = BigDecimal.valueOf(price).multiply(discountMultiplier); // Применяем множитель
        return discountedPrice.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // Округляем до целого числа
    }

}