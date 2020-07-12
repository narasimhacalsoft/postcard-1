package com.postcard.dao;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.postcard.model.Image;

public interface ImageDao {
    
	String createImage( MultipartFile file, long orderId, String imageId);
    
    List<Image> findallImage();
    
    Image findOne(Long imageId);
    
    void deleteImage(final Image image);

}
