package com.techstackgo.ecommerce.repository;

import com.techstackgo.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
//    @Query("Select o from order o where o.user.id=:userId and (o.orderStatus=PLACED or o.orderStatus=CONFIRMED or o.orderStatus=SHIPPED or o.orderStatus=DELIVERED)")
//    public List<Order> getUserOrders(@Param("userId") Long userId);
}
