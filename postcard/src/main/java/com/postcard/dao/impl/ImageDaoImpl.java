package com.postcard.dao.impl;

import java.sql.Blob;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.dao.ImageDao;
import com.postcard.dao.PostcardDao;
import com.postcard.model.Image;
import com.postcard.model.Postcard;

@Repository
public class ImageDaoImpl extends BaseDao implements ImageDao {
    
    @Value("${createImageQuery}")
    private String createImageQuery;
    
    @Value("${findOnePostcardQuery}")
    private String findOnePostcardQuery;
    
    @Value("${findallImageQuery}")
    private String findallImageQuery;
    
    @Value("${deletePostcardQuery}")
    private String deletePostcardQuery;
    

    @Override
	public void createImage(Image image) {
		// TODO Auto-generated method stub
		update(createImageQuery, "FrontImage");
	}

    @Override
	public List<Image> findallImage() {
		List<Image> objects = query(findallImageQuery, (rs,rowNum)->{
            Image obj = new Image();
            obj.setImageType("FrontImage");
            obj.setImageId(rs.getInt("imageId"));
            Blob blob = rs.getBlob("image");
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            obj.setImage(bytes);
            return obj;
            });
            return objects;
       }
	
	@Override
	public void deleteImage(Image image) {
		update(deletePostcardQuery, 1000);
    }
        
    
	@Override
	public Image findOne(Long imageId) {
		Image image =  queryForObject(findOnePostcardQuery, Image.class,1000);
        return image;
	}

}
