package com.postcard.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostcardOrderMapper implements RowMapper<PostcardOrder> {

	@Override
	public PostcardOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		PostcardOrder postcardOrder = new PostcardOrder();
		postcardOrder.setOrderId(rs.getInt("orderId"));
		postcardOrder.setImageId(rs.getInt("imageId"));
		postcardOrder.setSenderText(rs.getString("senderText"));
		postcardOrder.setSenderJson(rs.getString("senderJson"));
		postcardOrder.setBrandingText(rs.getString("brandingText"));
		postcardOrder.setBrandingJson(rs.getString("brandingJson"));
		postcardOrder.setCreatedDate(rs.getTimestamp("createdDate"));
		postcardOrder.setCreatedBy(rs.getString("createdBy"));
		postcardOrder.setUpdatedDate(rs.getTimestamp("updatedDate"));
		postcardOrder.setUpdatedBy(rs.getString("updatedBy"));
		postcardOrder.setOrderStatus(rs.getString("orderStatus"));
		return postcardOrder;
	}

}
