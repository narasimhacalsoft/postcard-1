package com.postcard.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.postcard.model.UpdateSenderAddressRequest;
import com.postcard.model.UpdateSenderTextRequest;
import com.postcard.util.SwissUtils;

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
	
	@Value("${updateSenderText}")
	private String updateSenderText;
	
	@Value("${updateBrandInfo}")
	private String updateBrandInfo;
	
	@Autowired
	SwissUtils swissUtils;
	
	@Override
	public PostcardOrder createPostcardOrder() { 
		
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			getMainJdbcTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(createPostcardOrderQuery,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, "DRAFT");
					ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
					ps.setString(3, swissUtils.getUsername());
					return ps;
				}
			}, holder);		
		PostcardOrder postcardOrder = new PostcardOrder();
		postcardOrder.setOrderStatus("DRAFT");
		postcardOrder.setOrderId(((BigInteger) holder.getKey()).intValue());
		return postcardOrder;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}

	@Override
	public void updatePostcardOrder(PostcardOrder postcardOrder) {
		postcardOrder.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcardOrder.setUpdatedBy(swissUtils.getUsername());
		// hardcoded for testing
		//update(updatePostcardOrderQuery, "sender text", "sender json", "branding text", "branding json", 2000);
		update(updatePostcardOrderQuery, postcardOrder.getSenderText(), postcardOrder.getSenderJson(), postcardOrder.getBrandingText(), postcardOrder.getBrandingJson(), postcardOrder.getImageId(),postcardOrder.getUpdatedDate(),postcardOrder.getUpdatedBy(), postcardOrder.getOrderId());
	}

	@Override
	public PostcardOrder findOne(Long orderId) {
		PostcardOrder postcardOrder = queryForObject(findOnePostcardOrderQuery, PostcardOrder.class, orderId);
		return postcardOrder;

	}

	@Override
	public void deletePostcardOrder(PostcardOrder postcardOrder) {
		// hardcoded for testing
		update(deletePostcardOrderQuery, postcardOrder.getOrderId());
	}

	@Override
	public List<PostcardOrder> findallPostcardOrder() {
		List<PostcardOrder> objects = query(findallPostcardOrderQuery, (rs, rowNum) -> {
			PostcardOrder obj = new PostcardOrder();
			obj.setOrderId(rs.getInt("orderId"));
			obj.setSenderText(rs.getString("senderText"));
			obj.setSenderJson(rs.getString("senderJson"));
			obj.setBrandingText(rs.getString("brandingText"));
			obj.setBrandingJson(rs.getString("brandingJson"));
			obj.setCreatedDate(rs.getTimestamp("createdDate"));
			obj.setCreatedBy(rs.getString("createdBy"));
			obj.setUpdatedDate(rs.getTimestamp("updatedDate"));
			obj.setUpdatedBy(rs.getString("updatedBy"));
			return obj;
		});
		return objects;
	}

	@Override
	public boolean updateSenderAddress(UpdateSenderAddressRequest request) {
		
		return getMainJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(updateSenderAddressInfo,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, new Gson().toJson(request.getSenderAddress()));
				ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
				ps.setString(3, swissUtils.getUsername());
				ps.setInt(4, request.getOrderId());
				return ps;
			}
		}) > 0;
		} 


	@Override
	public boolean updateBrandInfo(UpdateBrandRequest request) {
			return getMainJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(updateBrandInfo,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, new Gson().toJson(request.getBrandingText()));
					ps.setString(2, request.getBrandText());
					ps.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
					ps.setString(4, swissUtils.getUsername());
					ps.setInt(5, request.getOrderId());
					return ps;
				}
			}) >0;
		
		
	}

	@Override
	public boolean updateSenderText(UpdateSenderTextRequest request) {
			return getMainJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(updateSenderText,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, request.getSenderText());
					ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
					ps.setString(3, swissUtils.getUsername());
					ps.setInt(4, request.getOrderId());
					return ps;
				}
			}) >0;
		
	}

}
