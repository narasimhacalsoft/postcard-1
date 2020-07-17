package com.postcard.service;

import java.util.List;

import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderAddressRequest;
import com.postcard.model.UpdateSenderTextRequest;
import com.postcard.model.UpdateStatusResponse;

public interface PostcardOrderService {
    
	PostcardOrder createPostcardOrder();
    
    void updatePostcardOrder(final PostcardOrder order);
    
    List<PostcardOrder> findallPostcardOrder();
    
    PostcardOrder findOne(Long orderId);
    
    void deletePostcardOrder(final PostcardOrder order);
    
    UpdateStatusResponse updateBrandInfo(UpdateBrandRequest request);

    UpdateStatusResponse updateSenderText(UpdateSenderTextRequest request);
	
    UpdateStatusResponse updateSenderAddress(UpdateSenderAddressRequest request);

}

