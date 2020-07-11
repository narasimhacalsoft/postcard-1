package com.postcard.service;

import java.util.List;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.postcard.exception.ServiceException;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Postcard;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.model.SubmitOrderResponse;

public interface PostcardService {
    
    Long createPostcard(final Postcard postcard);
    
    Long updatePostcard(final Postcard postcard);
    
    List<Postcard> findallPostcard();
    
    Postcard findOne(Long card_id);
    
    void deletePostcard(final Postcard postcard);
    
    SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException;    

    List<GetAllPostcard> getAllPostcards();

    SubmitOrderResponse submitOrder(OAuth2RestTemplate postCardRestTemplate, long orderId ) throws ServiceException;
    
    void updatePostcardkey(final Postcard postcard);

}
