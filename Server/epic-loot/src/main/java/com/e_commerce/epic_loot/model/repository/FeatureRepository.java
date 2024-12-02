package com.e_commerce.epic_loot.model.repository;

import com.e_commerce.epic_loot.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}