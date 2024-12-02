package com.e_commerce.epic_loot.api.DTO.Product;

import com.e_commerce.epic_loot.model.SubCategory;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private Integer id;

    private String title;

    private String bannerUrl;

    private List<SubCategoryDTO> subCategoryTitles;

    public CategoryDTO(Integer id, String title, List<SubCategoryDTO> subCategories) {
        this.id = id;
        this.title = title;
        this.subCategoryTitles = subCategories;

    }

    public CategoryDTO(){

    }

}
