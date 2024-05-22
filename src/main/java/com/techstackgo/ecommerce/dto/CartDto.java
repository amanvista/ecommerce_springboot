package com.techstackgo.ecommerce.dto;

import com.techstackgo.ecommerce.model.CartItem;
import com.techstackgo.ecommerce.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private Set<CartItem> cartItems = new HashSet<>();
    private double totalPrice;
    private double totalItem;
    private double totalDiscount;
    private double finalPrice;
}
