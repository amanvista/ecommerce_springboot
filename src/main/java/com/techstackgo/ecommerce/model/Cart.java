package com.techstackgo.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    @Column(name="cart_items")
    private Set<CartItem> cartItems = new HashSet<>();
    @Column(name="total_price")
    private double totalPrice;
    @Column(name="total_item")
    private double totalItem;
    @Column(name="total_discount")
    private double totalDiscount;
    @Column(name="final_price")
    private double finalPrice;
}
