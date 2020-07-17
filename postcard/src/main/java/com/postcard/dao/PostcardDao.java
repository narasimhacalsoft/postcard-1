package com.postcard.dao;

import java.util.List;

import com.postcard.exception.ServiceException;
import com.postcard.model.OrderResponse;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardCarouselResponse;
import com.postcard.model.PostcardOrder;
import com.postcard.model.RecipientAddress;
import com.postcard.model.SaveRecipientResponse;

public interface PostcardDao {
    
    void createPostcard(final Postcard postcard);
    
    void updatePostcardState(final Postcard postcard);
    
    void updatePostcardApproval(final Postcard postcard);
    
    void updatePostcardkey(final Postcard postcard);
    
    List<Postcard> findallPostcard();
    
    Postcard findOneByCardKey(String cardKey);
    
    void deletePostcard(final Postcard postcard);
    
    SaveRecipientResponse saveRecipientAddress(Integer orderId, RecipientAddress request) throws ServiceException;
    
    List<Postcard> findPostcardByOrderId(Long orderId);

	List<PostcardCarouselResponse> getAllPostcardsByStatus();

	List<PostcardOrder> findallPostcardOrder(String from, String to);

	OrderResponse getAllPostcards(String from, String to);
    

}
