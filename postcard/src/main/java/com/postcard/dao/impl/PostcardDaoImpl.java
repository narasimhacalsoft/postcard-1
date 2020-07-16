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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.postcard.dao.BaseDao;
import com.postcard.dao.PostcardDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardMapper;
import com.postcard.model.PostcardOrder;
import com.postcard.model.PostcardOrderMapper;
import com.postcard.model.PostcardRequest;
import com.postcard.model.PostcardResponse;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderAddressRequest;
import com.postcard.service.PostcardOrderService;
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
	
	@Value("${selectGetallPostcards}")
	private String selectGetallPostcards;
	
	@Value("${postcardBaseURL}")
	String postcardBaseURL;
	
	@Value("${campaignKey}")
	String campaignKey;

	@Value("${createPostcardEndPoint}")
	String createPostcardEndPoint;
	
	@Value("${selectPostcardByStatus}")
	String selectPostcardByStatus;
	
	@Autowired
	SwissUtils swissUtils;
	
	@Autowired
	PostcardOrderService orderService;
	
	@Autowired
	OAuth2RestTemplate postCardRestTemplate;
	
	@Override
	public void createPostcard(Postcard postcard) {
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update(createPostcardQuery, 500, "card-key", "recipient", "submit status", "response", 1, "created",
				postcard.getCreatedDate(), postcard.getCreatedBy());
		// mainJdbcTemplate.update(sqlQuery, postcard.getcardId(),
		// postcard.getcardId(),postcard.getcardKey(),postcard.getrecipientJson(),postcard.getsubmissionStatus(),postcard.getResponse(),postcard.getAttempts(),postcard.getcardStatus());
	}

	@Override
	public void updatePostcardState(Postcard postcard) {
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update("update postcard set cardStatus =?,updatedDate=?,updatedBy=? where cardKey = ?", postcard.getCardStatus(), postcard.getUpdatedDate(), postcard.getUpdatedBy(),
				postcard.getCardKey());
	}
	
	@Override
	public void updatePostcardApproval(Postcard postcard) {
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update("update postcard set submissionStatus=? ,response=?,attempts=?, updatedDate=?, updatedBy=? where cardKey = ?", postcard.getSubmissionStatus(), postcard.getResponse(),
				postcard.getAttempts(), postcard.getUpdatedDate(), postcard.getUpdatedBy(),
				postcard.getCardKey());
	}

	@Override
	public void updatePostcardkey(Postcard postcard) {
		postcard.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		postcard.setUpdatedBy(swissUtils.getUsername());
		update("update postcard set cardKey =?,updatedDate=?,updatedBy=? where cardId = ?", postcard.getCardKey(),
				postcard.getUpdatedDate(), postcard.getUpdatedBy(), postcard.getCardId());

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
	public Postcard findOneByCardKey(String cardKey) {
		// String sql = "SELECT * FROM Postcard WHERE cardId = ?";
		Postcard postcard = queryForObject(findOnePostcardQuery, Postcard.class, cardKey);
		return postcard;

	}

	@Override
	public SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException {
		Postcard postcard = new Postcard();
		KeyHolder holder = new GeneratedKeyHolder();
		getMainJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(createPostcardForRecipientAddressQuery,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, request.getOrderId());
				ps.setString(2, new Gson().toJson(request.getRecipient()));
				ps.setString(3, "DRAFT");
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				ps.setString(5, swissUtils.getUsername());
				return ps;
			}
		}, holder);

		int cardId = holder.getKey().intValue();
		postcard.setCardId(cardId);
		SaveRecipientResponse response = new SaveRecipientResponse(request.getOrderId(), postcard);
		return response;
	}

	@Override
	public List<GetAllPostcard> getAllPostcards(String from,String to,String status) {
		List<GetAllPostcard> allPostcards = new ArrayList<>();
		List<PostcardOrder> postcardOrders = findallPostcardOrder(from,to,status);
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
	public List<PostcardOrder> findallPostcardOrder(String from,String to,String status) {
		
		if(StringUtils.isEmpty(from) || StringUtils.isEmpty(to)) {
			return query(selectPostcardByStatus, status, new PostcardOrderMapper());
		} else {
			return query(selectGetallPostcards, from, to, "SUBMITTED", new PostcardOrderMapper());
		}
		
	}

}
