package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.AddressException;
import com.techstackgo.ecommerce.exception.OrderException;
import com.techstackgo.ecommerce.model.*;
import com.techstackgo.ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService{
    private OrderRepository orderRepository;
    private CartService  cartService;
    private AddressService addressService;
    private UserService userService;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress)throws AddressException {
        shippingAddress.setUser(user);
        Address address = addressService.saveAddress(shippingAddress);
        user.getAddresses().add(address);
//        userService.saveUser(user);
        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem:cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
        }
        return null;
    }

    @Override
    public Order findOrderById(User user, Address shippingAddress) throws OrderException {
        return null;
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return null;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shipOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order deliverOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order cancelOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
