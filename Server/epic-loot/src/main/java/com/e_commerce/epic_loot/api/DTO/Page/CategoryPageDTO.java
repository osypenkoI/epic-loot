package com.e_commerce.epic_loot.api.DTO.Page;
import lombok.Data;

import java.util.List;

@Data
public class CategoryPageDTO {
    private String title;
    private String bannerUrl;
    private List<ProductGroupDTO> productGroups;
}
