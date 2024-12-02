package com.e_commerce.epic_loot.api.DTO.PurchaseOrder;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Double amount;
    private String currency;
    private String description;

}