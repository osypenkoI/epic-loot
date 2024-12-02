package com.e_commerce.epic_loot.model.repository;

import com.e_commerce.epic_loot.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    Optional<PurchaseOrder> findByLiqpayOrderId(String liqpayOrderId);
}