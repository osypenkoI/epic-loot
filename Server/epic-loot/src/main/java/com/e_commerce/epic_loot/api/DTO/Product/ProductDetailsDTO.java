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

    private String mainPictureUrl;

    private List<String> otherPictureUrl = new ArrayList<>();

    private List<FeatureDTO> features = new ArrayList<>();

    private List<CategoryDTO> categories = new ArrayList<>();

    private RequirementsDTO minimumRequirements;

    private RequirementsDTO recommendedRequirements;

}
