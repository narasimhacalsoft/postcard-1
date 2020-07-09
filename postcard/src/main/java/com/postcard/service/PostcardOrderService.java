package com.postcard.service;

import java.util.List;

import com.postcard.model.PostcardOrder;

public interface PostcardOrderService {
    
    void createPostcardOrder(final PostcardOrder order);
    
    void updatePostcardOrder(final PostcardOrder order);
    
    List<PostcardOrder> findallPostcardOrder();
    
    PostcardOrder findOne(Long orderId);
    
    void deletePostcardOrder(final PostcardOrder order);

}
