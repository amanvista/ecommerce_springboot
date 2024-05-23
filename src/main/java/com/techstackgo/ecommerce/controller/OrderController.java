package com.techstackgo.ecommerce.controller;

import com.techstackgo.ecommerce.exception.AddressException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Address;
import com.techstackgo.ecommerce.model.Order;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.service.OrderService;
import com.techstackgo.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
            @RequestHeader("Authorization") String jwt) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
}
