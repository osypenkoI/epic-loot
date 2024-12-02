package com.e_commerce.epic_loot.model.repository;

import com.e_commerce.epic_loot.model.Customer;
import com.e_commerce.epic_loot.model.Review;
import com.e_commerce.epic_loot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    boolean existsByCustomerAndProduct(Customer customer, Product product);

    List<Review> findAllByProductId(Integer productId);
}