package com.postcard.service;

import java.util.List;

import com.postcard.model.Postcard;

public interface PostcardService {
    
    Long createPostcard(final Postcard postcard);
    
    Long updatePostcard(final Postcard postcard);
    
    List<Postcard> findallPostcard();
    
    Postcard findOne(Long card_id);
    
    void deletePostcard(final Postcard postcard);

}
