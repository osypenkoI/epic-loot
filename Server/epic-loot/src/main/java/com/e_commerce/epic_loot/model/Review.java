package com.e_commerce.epic_loot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "review", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "product_id"})
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rating", nullable = false)
    private Boolean rating = false;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Пользователь обязателен
    private Customer customer;

}