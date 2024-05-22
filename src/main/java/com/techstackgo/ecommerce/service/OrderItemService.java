package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.CartItemException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.CartItem;
import com.techstackgo.ecommerce.model.OrderItem;
import com.techstackgo.ecommerce.model.Product;

public interface OrderItemService {
    public OrderItem createOrderItem(OrderItem orderItem);
}
