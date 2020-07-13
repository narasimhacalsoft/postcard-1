package com.postcard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.postcard.dao.BaseDao;
import com.postcard.dao.PostcardDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardMapper;
import com.postcard.model.PostcardOrder;
import com.postcard.model.RecipientAddress;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.util.SwissUtils;

@Repository
public class PostcardDaoImpl extends BaseDao implements PostcardDao {

	@Value("${createPostcardQuery}")
	private String createPostcardQuery;

	@Value("${updatePostcardQuery}")
	private String updatePostcardQuery;

	@Value("${findOnePostcardQuery}")
	private String findOnePostcardQuery;

	@Value("${findallPostcardQuery}")
	private String findallPostcardQuery;

	@Value("${deletePostcardQuery}")
	private String deletePostcardQuery;

	@Value("${createPostcardForRecipientAddressQuery}")
	private String createPostcardForRecipientAddressQuery;

	@Value("${findallPostcardByOrderidQuery}")
	private String findallPostcardByOrderidQuery;

	@Value("${findallPostcardOrderQuery}")
	private String findallPostcardOrderQuery;
	
	@Autowired
	SwissUtils swissUtils;

	@Override
	public void createPostcard(Postcard postcard) {
		// String sqlQuery = "insert into
		// postcard(orderId,cardKey,recipientJson,submissionStatus,response,attempts,cardStatus)
		// values (?,?,?,?,?,?,?,?)";
		// hardcoded for testing
		
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update(createPostcardQuery, 500, "card-key", "recipient", "submit status", "response", 1, "created",postcard.getCreatedDate(),postcard.getCreatedBy());
		// mainJdbcTemplate.update(sqlQuery, postcard.getcardId(),
		// postcard.getcardId(),postcard.getcardKey(),postcard.getrecipientJson(),postcard.getsubmissionStatus(),postcard.getResponse(),postcard.getAttempts(),postcard.getcardStatus());
	}

	@Override
	public void updatePostcard(Postcard postcard) {
		// String sqlQuery = "update postcard set recipientJson =?
		// ,submissionStatus=? ,response=?,attempts=?,cardStatus=? where cardKey
		// = ?";
		// hardcoded for testing
		
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update(updatePostcardQuery, "recipient", "submit status", "response", 1, "update", "card-key",postcard.getUpdatedDate(),postcard.getUpdatedBy());
		// mainJdbcTemplate.update(sqlQuery,
		// postcard.getrecipientJson(),postcard.getsubmissionStatus(),postcard.getResponse(),postcard.getAttempts(),postcard.getcardStatus());
	}

	@Override
	public void updatePostcardkey(Postcard postcard) {
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update("update postcard set cardKey =?,updatedDate=?,updatedBy=? where cardId = ?", postcard.getCardKey(),postcard.getUpdatedDate(),postcard.getUpdatedBy(), postcard.getCardId());

	}

	@Override
	public List<Postcard> findallPostcard() {
		// String sqlQuery = "select * from postcard";
		List<Postcard> objects = query(findallPostcardQuery, new PostcardMapper());
		return objects;
	}
	
	@Override
	public void deletePostcard(Postcard postcard) {
		// String sqlQuery = "delete from Postcard where cardId = ?";
		// update(sqlQuery, postcard.getcardId());
		// hardcoded for testing
		update(deletePostcardQuery, postcard.getCardId());
	}

	@Override
	public Postcard findOne(Long cardId) {
		// String sql = "SELECT * FROM Postcard WHERE cardId = ?";
		Postcard postcard = queryForObject(findOnePostcardQuery, Postcard.class, cardId);
		return postcard;

	}

	@Override
	public SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException {
		List<Postcard> postcards = new ArrayList<>();
		KeyHolder holder = new GeneratedKeyHolder();
		for (RecipientAddress address : request.getRecipients()) {
			getMainJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(createPostcardForRecipientAddressQuery,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, request.getOrderId());
					ps.setString(2, new Gson().toJson(address));
					ps.setString(3, "DRAFT");
					ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
					ps.setString(5, swissUtils.getUsername());
					return ps;
				}
			}, holder);

			int cardId = holder.getKey().intValue();
			Postcard postcard = new Postcard();
			postcard.setCardId(cardId);
			postcard.setSubmissionStatus("DRAFT");
			postcard.setRecipientJson(new Gson().toJson(address));
			postcards.add(postcard);

		}
		SaveRecipientResponse response = new SaveRecipientResponse(request.getOrderId(), postcards);
		return response;
	}

	@Override
	public List<GetAllPostcard> getAllPostcards() {
		List<GetAllPostcard> allPostcards = new ArrayList<>();
		List<PostcardOrder> postcardOrders = findallPostcardOrder();
		for (PostcardOrder postcardOrder : postcardOrders) {
			GetAllPostcard postcard = new GetAllPostcard();
			List<Postcard> listPostcard = findPostcardByOrderId(Long.valueOf(postcardOrder.getOrderId()));
			if (!CollectionUtils.isEmpty(listPostcard)) {
				postcard.setOrderId(postcardOrder.getOrderId());
				postcard.setPostcards(findPostcardByOrderId(Long.valueOf(postcardOrder.getOrderId())));
				postcard.setPostcardOrder(postcardOrder);
				allPostcards.add(postcard);
			}

		}

		return allPostcards;
	}

	@Override
	public List<Postcard> findPostcardByOrderId(Long orderId) {
		return query(findallPostcardByOrderidQuery, orderId, new PostcardMapper());
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

}
