package com.techstackgo.ecommerce.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name="categories")
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private Long id;
    @NotNull
    @Size(max=50)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="parent_category_id")
    private Category parentCategory;
    private int level;
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Product> products = new HashSet<>();
}
