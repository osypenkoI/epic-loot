package com.e_commerce.epic_loot.api.DTO.Product;

import lombok.Data;

import java.util.List;

@Data
public class CriteriaDTO {
    private Integer customerId;

    private List<Integer> productsId;

    private String criteria;

}
