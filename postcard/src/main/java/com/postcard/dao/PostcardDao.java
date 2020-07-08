package com.postcard.dao;

import java.util.List;

import com.postcard.model.Postcard;

public interface PostcardDao {
    
    void createPostcard(final Postcard postcard);
    
    void updatePostcard(final Postcard postcard);
    
    List<Postcard> findallPostcard();
    
    Postcard findOne(Long card_id);
    
    void deletePostcard(final Postcard postcard);

}
