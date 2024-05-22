package com.techstackgo.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
//    @ManyToOne(fetch = FetchType.LAZY) // Many products belong to one category
//    @JoinColumn(name = "category_id") // Foreign key column in the product table
//    private Category category;
    private LocalDateTime createdAt;
}
