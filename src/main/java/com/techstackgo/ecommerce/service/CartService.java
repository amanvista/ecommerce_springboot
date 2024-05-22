package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.AddItemRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.User;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException, UserException;
    public Cart findUserCart(Long userId);



}
