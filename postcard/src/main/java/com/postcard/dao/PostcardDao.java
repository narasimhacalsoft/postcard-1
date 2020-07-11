package com.postcard.dao;

import java.util.List;

import com.postcard.exception.ServiceException;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardOrder;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;

public interface PostcardDao {
    
    void createPostcard(final Postcard postcard);
    
    void updatePostcard(final Postcard postcard);
    
    List<Postcard> findallPostcard();
    
    Postcard findOne(Long card_id);
    
    void deletePostcard(final Postcard postcard);
    
    SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException;
    
    List<GetAllPostcard> getAllPostcards();
    
    List<Postcard> findPostcardByOrderId(Long orderId);
    
    List<PostcardOrder> findallPostcardOrder();
    

}
