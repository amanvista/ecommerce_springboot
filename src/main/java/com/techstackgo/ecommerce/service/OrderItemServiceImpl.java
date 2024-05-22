package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.model.OrderItem;
import com.techstackgo.ecommerce.repository.OrderItemRepository;

public class OrderItemServiceImpl implements OrderItemService{
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }
}
