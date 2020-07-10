package com.postcard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.postcard.dao.BaseDao;
import com.postcard.dao.PostcardOrderDao;
import com.postcard.model.PostcardOrder;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderRequest;

@Repository
public class PostcardOrderDaoImpl extends BaseDao implements PostcardOrderDao {
    
    @Value("${createPostcardOrderQuery}")
    private String createPostcardOrderQuery;
    
    @Value("${updatePostcardOrderQuery}")
    private String updatePostcardOrderQuery;
    
    @Value("${findOnePostcardOrderQuery}")
    private String findOnePostcardOrderQuery;
    
    @Value("${findallPostcardOrderQuery}")
    private String findallPostcardOrderQuery;
    
    @Value("${deletePostcardOrderQuery}")
    private String deletePostcardOrderQuery;
    
    @Value("${updateSenderAddressInfo}")
    private String updateSenderAddressInfo;
    
    @Value("${updateBrandInfo}")
    private String updateBrandInfo;
    
    @Override
    public void createPostcardOrder(PostcardOrder postcardOrder) {
        // hardcoded for testing
        update(createPostcardOrderQuery, "sender text", "sender json", "branding text", "branding json");
    }

    @Override
    public void updatePostcardOrder(PostcardOrder postcardOrder) {
        //hardcoded for testing
        update(updatePostcardOrderQuery, "sender text", "sender json", "branding text", "branding json", 2000);
        //mainJdbcTemplate.update(sqlQuery, postcardOrder.getOrderId(), postcardOrder.getSenderText(), postcardOrder.getSenderJson(), postcardOrder.getBrandingText(), postcardOrder.getBrandingJson());
        }

    @Override
    public PostcardOrder findOne(Long orderId) {
        PostcardOrder postcardOrder =  queryForObject(findOnePostcardOrderQuery, PostcardOrder.class,1000);
        return postcardOrder;
    
    }

    @Override
    public void deletePostcardOrder(PostcardOrder postcardOrder) {
        //hardcoded for testing
        update(deletePostcardOrderQuery, 1000);
     }
    
    @Override
    public List<PostcardOrder> findallPostcardOrder() {
        List<PostcardOrder> objects = query(findallPostcardOrderQuery, (rs,rowNum)->{
            PostcardOrder obj = new PostcardOrder();
            obj.setOrderId(rs.getInt("orderId"));
            obj.setSenderText(rs.getString("senderText"));
            obj.setSenderJson(rs.getString("senderJson"));
            obj.setBrandingText(rs.getString("brandingText"));
            obj.setBrandingJson(rs.getString("brandingJson"));
            return obj;
            });
            return objects;
       }
    

    @Override
	public String updateSenderAddress(UpdateSenderRequest request) {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			getMainJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(updateSenderAddressInfo,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, new Gson().toJson(request.getSenderAddress()));
					ps.setString(2, request.getSenderText());
					ps.setInt(3, request.getOrderId());
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "Sender information updated successfully";
		
	}
	
	@Override
	public String updateBrandInfo(UpdateBrandRequest request) {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			getMainJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(updateBrandInfo,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, new Gson().toJson(request.getBrandingText()));
					ps.setString(2, request.getBrandText());
					ps.setInt(3, request.getOrderId());
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "Brand information updated successfully";
		
	}

    
    

}
