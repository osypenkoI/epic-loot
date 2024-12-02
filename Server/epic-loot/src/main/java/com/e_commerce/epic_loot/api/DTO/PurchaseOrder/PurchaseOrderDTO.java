package com.e_commerce.epic_loot.api.DTO.PurchaseOrder;

import lombok.Data;

@Data
public class PurchaseOrderDTO {
    private Integer customerId;
    private Integer productId;
    private Integer price;

    public PurchaseOrderDTO(Integer customerId, Integer productId, Integer price) {
        this.customerId = customerId;
        this.productId = productId;
        this.price = price;
    }
}
