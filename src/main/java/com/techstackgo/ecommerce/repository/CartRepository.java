package com.techstackgo.ecommerce.repository;

import com.techstackgo.ecommerce.model.Cart;
import com.techstackgo.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("Select c from Cart c where c.user.id=:userId")
    public Cart findByUserId(@Param("userId") Long userId);
//    @Query("SELECTE ci from CartItem ci where ci.cart = :cart and ci.product=:product and ci.size=:size  and ci.userId=:userId")
//    private CartItem isCartItemExist(@Param(("cart")Cart cart,@Param("product") Product product,@Param("size")String size),@Param("userId") Long userId);
}
