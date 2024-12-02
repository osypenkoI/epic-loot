package com.e_commerce.epic_loot.api.DTO.Product;

import lombok.Data;

@Data
public class ReviewDTO {

    private Integer productId;
    private Boolean rating;
    private String reviewText;
}
