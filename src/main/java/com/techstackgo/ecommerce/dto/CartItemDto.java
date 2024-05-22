package com.techstackgo.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private int productId;
    private String size;
    private int quantity;
    private int price;
}
