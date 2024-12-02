package com.e_commerce.epic_loot.model.repository;

import com.e_commerce.epic_loot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}