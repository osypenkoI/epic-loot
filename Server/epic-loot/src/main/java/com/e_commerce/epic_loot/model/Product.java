package com.e_commerce.epic_loot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "product")
@Check(constraints = "discount >= 0 AND discount <= 100")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "title", unique = true, length = 50, nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "main_picture_url", length = 255, nullable = false)
    private String mainPictureUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_other_picture_urls", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "picture_url", length = 255)
    private List<String> otherPictureUrl = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "product_features",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Collection<Feature> features = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "category_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Collection<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private Collection<Review> reviews;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private Collection<PurchaseOrder> purchaseOrders;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "minimum_requirements_id", nullable = false)
    private MinimumRequirements minimumRequirements;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "recommended_requirements_id", nullable = false)
    private RecommendedRequirements recommendedRequirements;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "sub_category_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "subCategory_id")
    )
    private Collection<SubCategory> subCategories = new ArrayList<>();

}
