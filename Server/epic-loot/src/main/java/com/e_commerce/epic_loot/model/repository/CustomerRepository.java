package com.e_commerce.epic_loot.model.repository;

import com.e_commerce.epic_loot.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByUsername(String username);

    @Query("SELECT c.wishlist FROM Customer c WHERE c.id = :customerId")
    List<Integer> findByIdWishList(Integer customerId);
    @Query("SELECT c.purchasedGames FROM Customer c WHERE c.id = :customerId")
    List<Integer> findByIdPurchasedGames(Integer customerId);


}