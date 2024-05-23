package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.AddressException;
import com.techstackgo.ecommerce.exception.OrderException;
import com.techstackgo.ecommerce.model.Address;
import com.techstackgo.ecommerce.model.Order;
import com.techstackgo.ecommerce.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress) throws AddressException;

    public Order findOrderById(User user, Address shippingAddress) throws OrderException;

    public List<Order> userOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmOrder(Long orderId) throws OrderException;

    public Order shipOrder(Long orderId) throws OrderException;

    public Order deliverOrder(Long orderId) throws OrderException;

    public Order cancelOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
