package com.techstackgo.ecommerce.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private int price;
    @Column(name="discounted_price")
    private int discountedPrice;
    @Column(name="discount_present")
    private int discountPresent;
    private int quantity;
    private String brand;
    private String color;
    @Embedded
    @ElementCollection
    private Set<Size> sizes = new HashSet<>();
    @Column(name="image_url")
    private String imageUrl;
    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();



}
