package com.postcard.service;

import java.util.List;

import com.postcard.model.Order;

public interface OrderService {
    
    void createOrder(final Order order);
    
    void updateOrder(final Order order);
    
    List<Order> findallOrder();
    
    Order findOne(Long orderId);
    
    void deletePostcard(final Order order);

}
