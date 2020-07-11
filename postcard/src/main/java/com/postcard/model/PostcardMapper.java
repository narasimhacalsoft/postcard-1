package com.postcard.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostcardMapper implements RowMapper<Postcard> {

	public Postcard mapRow(ResultSet rs, int rowNum) throws SQLException {
		Postcard postcard = new Postcard();
		//postcard.setOrderId(rs.getInt("orderId"));
		postcard.setCardId(rs.getInt("cardId"));
		postcard.setCardKey(rs.getString("cardKey"));
		postcard.setRecipientJson(rs.getString("recipientJson"));
		postcard.setSubmissionStatus(rs.getString("submissionStatus"));
		postcard.setResponse(rs.getString("response"));
		postcard.setAttempts(rs.getInt("attempts"));
		postcard.setCardStatus(rs.getString("cardStatus"));
	      return postcard;
	   }
}
