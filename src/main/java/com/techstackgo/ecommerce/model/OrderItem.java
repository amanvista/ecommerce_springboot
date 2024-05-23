package com.techstackgo.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @ManyToOne
    private User user;
    // @OneToMany(mappedBy = "orderItems", cascade = CascadeType.ALL)
    // private List<Order> orders = new ArrayList<>();
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private int price;
    private int quantity;
}
