package com.postcard.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.postcard.exception.ServiceException;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardOrder;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;

public interface PostcardService {
    
    SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException;    

    List<GetAllPostcard> getAllPostcards(String from,String to,String status);

    PostcardOrder submitOrder(OAuth2RestTemplate postCardRestTemplate, long orderId, String cardKey ) throws ServiceException;
    
    void updateRecipient(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard);
    
    void updateSender(OAuth2RestTemplate postCardRestTemplate, String postcardKey, PostcardOrder postcardOrder);
    
    void updateSenderText(OAuth2RestTemplate postCardRestTemplate, String postcardKey, PostcardOrder postcardOrder); 
    
    void updateBrandingText(OAuth2RestTemplate postCardRestTemplate, String postcardKey, PostcardOrder postcardOrder);
    
    void updateImage(OAuth2RestTemplate postCardRestTemplate, String postcardKey, PostcardOrder postcardOrder);
    
    void updateStampImage(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard);
    
    void updateBrandingImage(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard);

    ResponseEntity<?> approvePostcard(OAuth2RestTemplate postCardRestTemplate, Postcard postcard);
    
    ResponseEntity<?> getPostcardState(OAuth2RestTemplate postCardRestTemplate, Postcard postcard);
    

}
