package com.techstackgo.ecommerce.controller;

import com.techstackgo.ecommerce.dto.AddItemRequest;
import com.techstackgo.ecommerce.dto.CartResponse;
import com.techstackgo.ecommerce.exception.CartItemException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.CartItem;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.service.CartItemService;
import com.techstackgo.ecommerce.service.CartService;
import com.techstackgo.ecommerce.service.UserService;
import com.techstackgo.ecommerce.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart_item")
@RestController
public class CartItemController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Cart Item")
    public ResponseEntity<CartResponse> updateCartItem(@PathVariable Long cartItemId,
            @RequestBody CartItem updatedCartItem, @RequestHeader("Authorization") String jwt)
            throws UserException, CartItemException {
        User user = userService.getByUsername(jwt);
        // CartItem cartItem = cartItemService.findCartItemById(cartItemId);
        cartItemService.updateCartItem(user.getId(), cartItemId, updatedCartItem);
        CartResponse res = new CartResponse();
        res.setMessage("Updated Cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Item from Cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Delete Item")
    public ResponseEntity<CartResponse> deleteCartItem(@PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.getByUsername(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        CartResponse res = new CartResponse();
        res.setMessage("Deleted item from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
