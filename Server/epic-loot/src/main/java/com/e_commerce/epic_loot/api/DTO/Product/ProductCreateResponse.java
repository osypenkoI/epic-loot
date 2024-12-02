package com.e_commerce.epic_loot.api.DTO.Product;

import lombok.Data;

import java.util.List;

@Data
public class ProductCreateResponse {
    private String title;
    private String mainImageUrl;
    private List<String> otherImageUrl;


}
