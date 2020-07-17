package com.postcard.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postcard.dao.PostcardOrderDao;
import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderAddressRequest;
import com.postcard.model.UpdateSenderTextRequest;
import com.postcard.model.UpdateStatusResponse;
import com.postcard.service.PostcardOrderService;

@Service
public class PostcardOrderServiceImpl implements PostcardOrderService{
    
    @Autowired
    PostcardOrderDao orderDao;
    
    @Autowired
    PostcardOrderDao postcardOrderDao;
    
    @Override
    public PostcardOrder createPostcardOrder() {
    	PostcardOrder postcardOrder = orderDao.createPostcardOrder();
        return postcardOrder;
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
	public UpdateStatusResponse updateSenderAddress(UpdateSenderAddressRequest request) {
    	String status = "";
		if(postcardOrderDao.updateSenderAddress(request)) {
			status = "SenderAddress updated successfully.";
		}
		return new UpdateStatusResponse(status);
	}

	@Override
	public UpdateStatusResponse updateBrandInfo(UpdateBrandRequest request) {
		String status = "";
		if(postcardOrderDao.updateBrandInfo(request)) {
			status = "Brandinfo updated successfully.";
		}
		return new UpdateStatusResponse(status);
	}

	@Override
	public UpdateStatusResponse updateSenderText(UpdateSenderTextRequest request) {
		String status = "";
		if(postcardOrderDao.updateSenderText(request)) {
			status = "SenderText updated successfully.";
		}
		return new UpdateStatusResponse(status);
	}



}
