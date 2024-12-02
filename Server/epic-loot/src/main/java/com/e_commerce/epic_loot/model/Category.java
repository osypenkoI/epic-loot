package com.e_commerce.epic_loot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image_url", nullable = false)
    private String bannerUrl;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private Collection<SubCategory> subCategories;

}