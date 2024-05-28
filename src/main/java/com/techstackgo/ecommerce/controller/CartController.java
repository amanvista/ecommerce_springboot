package com.techstackgo.ecommerce.controller;

import com.techstackgo.ecommerce.dto.AddItemRequest;
import com.techstackgo.ecommerce.dto.AuthResponse;
import com.techstackgo.ecommerce.dto.CartDto;
import com.techstackgo.ecommerce.dto.CartResponse;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.service.CartService;
import com.techstackgo.ecommerce.service.UserService;
import com.techstackgo.ecommerce.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart Management", description = "Find User Cart, Add Item to Cart")
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "Find Cart By User ID")
    public ResponseEntity<CartDto> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getByUsername(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        // Check if the cart is null and handle accordingly
        if (cart == null) {
            throw new RuntimeException("No Cart Found");
        }
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setTotalDiscount(cart.getTotalDiscount());
        dto.setCartItems(cart.getCartItems());
        dto.setFinalPrice(cart.getFinalPrice());
        dto.setTotalItem(cart.getTotalItem());
        dto.setTotalPrice(cart.getTotalPrice());
        return new ResponseEntity<CartDto>(dto, HttpStatus.OK);
    }

    @PutMapping("/add")
    @Operation(description = "Add Item to Cart")
    public ResponseEntity<CartResponse> addItemToCart(@RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.getByUsername(jwt);
        cartService.addCartItem(user.getId(), req);
        CartResponse cartResponse = new CartResponse();
        cartResponse.setMessage("Item added to cart");
        cartResponse.setStatus(true);
        return new ResponseEntity<CartResponse>(cartResponse, HttpStatus.OK);
    }
}
