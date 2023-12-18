package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.CreateOrderRequest;
import com.hlteam.naturezen.entity.Order;

import java.util.List;


public interface OrderService {
    
    void placeOrder(CreateOrderRequest request);

    List<Order> getList();
    
    List<Order> getOrderByUser(String username);
}
