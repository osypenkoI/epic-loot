package com.e_commerce.epic_loot.api.DTO.Page;
import lombok.Data;

import java.util.List;

@Data
public class ProductGroupDTO {

    private String title;
    private List<ProductCardDTO> products;
}
