package com.e_commerce.epic_loot.model;

import com.e_commerce.epic_loot.enumEntity.AuthorityRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Check;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "customer")
@Check(constraints = "LENGTH(username) >= 3 AND LENGTH(password) >= 8")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_roles", joinColumns = @JoinColumn(name = "customer_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Collection<AuthorityRole> roles = new ArrayList<>();


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "customer_purchased_games", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "product_id")
    private Collection<Integer> purchasedGames = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "customer_wishlist", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "product_id")
    private Collection<Integer> wishlist = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Review> reviews = new ArrayList<>();
}
