package com.techstackgo.ecommerce.repository;

import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.CartItem;
import com.techstackgo.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci " +
            "WHERE ci.cart = :cart " +
            "AND ci.product = :product " +
            "AND ci.userId = :userId")
    CartItem isCartItemExist(@Param("cart") Cart cart,
                             @Param("product") Product product,
                             @Param("userId") Long userId);
}
