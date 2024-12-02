package com.e_commerce.epic_loot.api.DTO.Product;

import com.e_commerce.epic_loot.model.Category;
import com.e_commerce.epic_loot.model.Feature;
import com.e_commerce.epic_loot.model.Product;
import lombok.Data;

@Data
public class FeatureDTO {

    public FeatureDTO(Feature feature) {
        this.id = feature.getId();
        this.title = feature.getTitle();
    }

    public FeatureDTO(){

    }

    private Integer id;

    private String title;
}
