package com.techstackgo.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    @JsonIgnore
    private Product product;
    @Column(name="rating")
    private double rating;
    private LocalDateTime createdAt;

}
