package com.postcard.service;

import java.util.List;

import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderAddressRequest;
import com.postcard.model.UpdateSenderTextRequest;

public interface PostcardOrderService {
    
	PostcardOrder createPostcardOrder();
    
    void updatePostcardOrder(final PostcardOrder order);
    
    List<PostcardOrder> findallPostcardOrder();
    
    PostcardOrder findOne(Long orderId);
    
    void deletePostcardOrder(final PostcardOrder order);
    
    String updateBrandInfo(UpdateBrandRequest request);

	String updateSenderText(UpdateSenderTextRequest request);
	
	String updateSenderAddress(UpdateSenderAddressRequest request);

}

