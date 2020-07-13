package com.postcard.service.impl;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.postcard.dao.ImageDao;
import com.postcard.model.Image;
import com.postcard.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
    
    @Autowired
    ImageDao imageDao;
    

	@Override
	public String createImage(MultipartFile file, long orderId, String imageType) {
		String imageId = imageDao.createImage(file,orderId,imageType);
		return imageId;
	}

	@Override
	public List<Image> findallImage() {
		List<Image> images = imageDao.findallImage();
        return images;
	}

   
	@Override
	public Image findOne(Long imageId) {
		Image image = imageDao.findOne(imageId);
        return image;
	}

	@Override
	public void deleteImage(Image image) {
		imageDao.deleteImage(image); 
	}

}
