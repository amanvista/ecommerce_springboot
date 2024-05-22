package com.techstackgo.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequest {
    private Long productId;
    private String size;
    private int quantity;
    private Integer price;
}
