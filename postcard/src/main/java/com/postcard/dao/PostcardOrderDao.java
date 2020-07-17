package com.postcard.dao;

import java.util.List;

import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderAddressRequest;
import com.postcard.model.UpdateSenderTextRequest;

public interface PostcardOrderDao {
    
	PostcardOrder createPostcardOrder();
    
    void updatePostcardOrder(final PostcardOrder order);
    
    List<PostcardOrder> findallPostcardOrder();
    
    PostcardOrder findOne(Long orderId);
    
    void deletePostcardOrder(final PostcardOrder order);
    
    boolean updateSenderAddress(UpdateSenderAddressRequest request);
    
    boolean updateBrandInfo(UpdateBrandRequest request);
    
    boolean updateSenderText(UpdateSenderTextRequest request);
    
    
  
}
