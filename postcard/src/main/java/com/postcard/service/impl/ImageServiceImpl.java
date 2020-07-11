package com.postcard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postcard.dao.ImageDao;
import com.postcard.dao.PostcardDao;
import com.postcard.model.Image;
import com.postcard.model.Postcard;
import com.postcard.service.ImageService;
import com.postcard.service.PostcardService;

@Service
public class ImageServiceImpl implements ImageService{
    
    @Autowired
    ImageDao imageDao;
    

	@Override
	public void createImage(Image image) {
		imageDao.createImage(image);
		
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
