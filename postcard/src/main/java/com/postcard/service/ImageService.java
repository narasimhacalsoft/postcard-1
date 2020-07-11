package com.postcard.service;

import java.util.List;

import com.postcard.model.Image;

public interface ImageService {
    
    void createImage(final Image image);
    
    List<Image> findallImage();
    
    Image findOne(Long imageId);
    
    void deleteImage(final Image image);

}
