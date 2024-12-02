package com.e_commerce.epic_loot.api.DTO.Product;

import lombok.Data;

@Data
public class SubCategoryDTO {
    private Integer id;
    private String title;
    private String bannerUrl;
    private Integer categoryId;

    public SubCategoryDTO(Integer id, String title, Integer categoryId) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
    }

    public SubCategoryDTO() {
    }
}
