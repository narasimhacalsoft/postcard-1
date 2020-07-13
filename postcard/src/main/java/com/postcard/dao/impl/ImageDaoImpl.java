package com.postcard.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.postcard.dao.BaseDao;
import com.postcard.dao.ImageDao;
import com.postcard.dao.PostcardOrderDao;
import com.postcard.model.Image;
import com.postcard.model.PostcardOrder;

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
    
    @Autowired
    PostcardOrderDao postcardOrderDao;

    @Override
	public String createImage(MultipartFile file, long orderId, String imageType) {
		// TODO Auto-generated method stub
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			byte [] byteArr=file.getBytes();
	        Blob blob = new SerialBlob(byteArr );
			getMainJdbcTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement("insert into image(image, imageType) values (?,?)",
							Statement.RETURN_GENERATED_KEYS);
					ps.setBlob(1, blob);
					ps.setString(2, imageType);
					return ps;
				}
			}, holder);		
		String imageId = holder.getKey().toString();
		//update postcardorder with the imageId
		PostcardOrder postcardOrder = new PostcardOrder();
		postcardOrder.setOrderId((int) orderId);
		postcardOrder.setImageId(Integer.parseInt(imageId));
		postcardOrderDao.updatePostcardOrder(postcardOrder);
		return imageId;

		}
		catch (Exception e) {
			System.out.println(e);
			return e.getMessage();
		}

	}

    @Override
	public List<Image> findallImage() {
		List<Image> objects = query(findallImageQuery, (rs,rowNum)->{
            Image obj = new Image();
            obj.setImageType("FrontImage");
            obj.setImageId(rs.getInt("imageId"));
            Blob blob = rs.getBlob("image");
            if(blob!= null && blob.length() > 0) {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            obj.setImage(bytes);
            }
            return obj;
            });
            return objects;
       }
	
	@Override
	public void deleteImage(Image image) {
		update(deletePostcardQuery, image.getImageId());
    }
        
    
	@Override
	public Image findOne(Long imageId) {
		Image image =  queryForObject(findOnePostcardQuery, Image.class,imageId);
        return image;
	}

}
