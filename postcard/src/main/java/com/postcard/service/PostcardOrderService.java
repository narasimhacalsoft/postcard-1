package com.postcard.service;

import java.util.List;

import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderRequest;

public interface PostcardOrderService {
    
	PostcardOrder createPostcardOrder(final long imageId);
    
    void updatePostcardOrder(final PostcardOrder order);
    
    List<PostcardOrder> findallPostcardOrder();
    
    PostcardOrder findOne(Long orderId);
    
    void deletePostcardOrder(final PostcardOrder order);
    
    String updateSenderAddress(UpdateSenderRequest request);
    
    String updateBrandInfo(UpdateBrandRequest request);

}

