package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.domain.OrderStatus;
import com.techstackgo.ecommerce.domain.PaymentStatus;
import com.techstackgo.ecommerce.exception.AddressException;
import com.techstackgo.ecommerce.exception.OrderException;
import com.techstackgo.ecommerce.model.*;
import com.techstackgo.ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    private OrderRepository orderRepository;
    private CartService cartService;
    private AddressService addressService;
    private UserService userService;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress) throws AddressException {
        shippingAddress.setUser(user);
        Address address = addressService.saveAddress(shippingAddress);
        user.getAddresses().add(address);
        // userService.saveUser(user);
        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
        }
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        // createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(createdOrder);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(savedOrder.getOrderId());
            orderItemRepository.save(orderItem);
        }
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return orderRepository.getReferenceById(orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return null;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order shipOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliverOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
