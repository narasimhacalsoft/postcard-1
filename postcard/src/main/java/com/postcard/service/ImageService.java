package com.postcard.service;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.postcard.model.Image;

public interface ImageService {
    
	String createImage(MultipartFile filemfile, long orderId, String imageType);
    
    List<Image> findallImage();
    
    Image findOne(Long imageId);
    
    void deleteImage(final Image image);

}
