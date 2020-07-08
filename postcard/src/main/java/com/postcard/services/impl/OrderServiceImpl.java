package com.postcard.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postcard.dao.OrderDao;
import com.postcard.dao.PostcardDao;
import com.postcard.model.Order;
import com.postcard.model.Postcard;
import com.postcard.service.OrderService;
import com.postcard.service.PostcardService;

@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    OrderDao orderDao;
    
    
    @Override
    public void createOrder(Order order) {
        orderDao.createOrder(order);       
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findallOrder() {
        List<Order> orderList = orderDao.findallOrder();
        return orderList;
    }

    @Override
    public Order findOne(Long orderId) {
        Order order = orderDao.findOne(orderId);
        return order;
    }

    @Override
    public void deletePostcard(Order order) {
        orderDao.deletePostcard(order);
    }

}
