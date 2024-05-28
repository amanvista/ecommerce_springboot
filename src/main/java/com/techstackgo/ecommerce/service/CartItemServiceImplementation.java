package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.CartItemException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.CartItem;
import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.CartItemRepository;
import com.techstackgo.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem updatedCartItem)
            throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());
        if (user.getId().equals(userId)) {
            item.setQuantity(updatedCartItem.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        User reqUser = userService.findUserById(userId);
        if (user.getId().equals(reqUser.getId())) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new UserException("You can't remove another user Item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("Cart Item not found with id" + cartItemId);
    }
}
