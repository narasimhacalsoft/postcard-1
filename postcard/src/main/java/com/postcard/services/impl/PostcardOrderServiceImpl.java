package com.postcard.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postcard.dao.PostcardOrderDao;
import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderRequest;
import com.postcard.service.PostcardOrderService;

@Service
public class PostcardOrderServiceImpl implements PostcardOrderService{
    
    @Autowired
    PostcardOrderDao orderDao;
    
    @Autowired
    PostcardOrderDao postcardOrderDao;
    
    @Override
    public String createPostcardOrder(long imageId) {
        String postcardOrderId = orderDao.createPostcardOrder(imageId);
        return postcardOrderId;
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
    
    @Override
	public String updateSenderAddress(UpdateSenderRequest request) {
		return postcardOrderDao.updateSenderAddress(request);
	}

	@Override
	public String updateBrandInfo(UpdateBrandRequest request) {
		return postcardOrderDao.updateBrandInfo(request);
	}



}
