package com.postcard.dao;

import java.util.List;

import com.postcard.model.Image;

public interface ImageDao {
    
    void createImage(final Image image);
    
    List<Image> findallImage();
    
    Image findOne(Long imageId);
    
    void deleteImage(final Image image);

}
