package com.postcard.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postcard.dao.PostcardOrderDao;
import com.postcard.model.PostcardOrder;
import com.postcard.service.PostcardOrderService;

@Service
public class PostcardOrderServiceImpl implements PostcardOrderService{
    
    @Autowired
    PostcardOrderDao orderDao;
    
    @Override
    public void createPostcardOrder(PostcardOrder order) {
        orderDao.createPostcardOrder(order);       
    }

    @Override
    public void updatePostcardOrder(PostcardOrder order) {
        orderDao.updatePostcardOrder(order);
    }

    @Override
    public List<PostcardOrder> findallPostcardOrder() {
        List<PostcardOrder> orderList = orderDao.findallPostcardOrder();
        return orderList;
    }

    @Override
    public PostcardOrder findOne(Long orderId) {
        PostcardOrder order = orderDao.findOne(orderId);
        return order;
    }

    @Override
    public void deletePostcardOrder(PostcardOrder order) {
        orderDao.deletePostcardOrder(order);
    }

}
