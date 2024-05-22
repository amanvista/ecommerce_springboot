package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.AddItemRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.CartItem;
import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class CartServiceImplementation implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException, UserException {
        Cart cart =  cartRepository.findByUserId(userId);
        User user = userService.findUserById(userId);
        // If the cart does not exist, create a new cart
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new HashSet<>());
            cart.setTotalPrice(0);
            cart.setTotalItem(0);
            cart.setTotalDiscount(0);
            cart.setFinalPrice(0);
            cartRepository.save(cart); // Save the new cart to the repository
        }
        // Retrieve the product by productId
        Product product = productService.findProductById(req.getProductId());

        // Check if the cart item already exists
        CartItem isPresent = cartItemService.isCartItemExist(cart, product, userId);
        if(isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            int price = req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
            cartRepository.save(cart); // Save the new cart to the repository
        }
        return "Item Add to Cart";
    }
    @Override
    public Cart findUserCart(Long userId) {
        Cart cart=null;
       try {
           cart = cartRepository.findByUserId(userId);
           return cart;
//           int totalPrice = 0;
//           int totalDiscountedPrice = 0;
//           int totalItem = 0;
//           if(cart!=null) {
//               for (CartItem cartItem : cart.getCartItems()) {
//                   totalPrice += cartItem.getPrice();
//                   totalDiscountedPrice += cartItem.getDiscountedPrice();
//                   totalItem += cartItem.getQuantity();
//               }
//
//               cart.setTotalItem(totalItem);
//               cart.setTotalDiscount(totalDiscountedPrice);
//               cart.setFinalPrice(totalPrice - totalDiscountedPrice);
//               cartRepository.save(cart);

       }catch (Exception e){
           e.printStackTrace();
       }
        throw new RuntimeException("Cart is not found");
    }
}
