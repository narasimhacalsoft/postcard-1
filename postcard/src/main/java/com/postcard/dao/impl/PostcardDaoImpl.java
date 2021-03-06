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
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.postcard.dao.BaseDao;
import com.postcard.dao.PostcardDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.OrderResponse;
import com.postcard.model.Order;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardCarouselResponse;
import com.postcard.model.PostcardMapper;
import com.postcard.model.PostcardOrder;
import com.postcard.model.PostcardOrderMapper;
import com.postcard.model.RecipientAddress;
import com.postcard.model.PostcardDetails;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.model.SenderAddress;
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
	public SaveRecipientResponse saveRecipientAddress(Integer orderId, RecipientAddress request) throws ServiceException {
	
		Postcard postcard = new Postcard();
		KeyHolder holder = new GeneratedKeyHolder();
		getMainJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(createPostcardForRecipientAddressQuery,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, orderId);
				ps.setString(2, new Gson().toJson(request));
				ps.setString(3, "DRAFT");
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				ps.setString(5, swissUtils.getUsername());
				return ps;
			}
		}, holder);

		int cardId = holder.getKey().intValue();
		postcard.setCardId(cardId);
		SaveRecipientResponse response = new SaveRecipientResponse(orderId, postcard);
		return response;
	}

	@Override
	public OrderResponse getAllPostcards(String from, String to) {
		OrderResponse finalResponse = new OrderResponse();
		List<PostcardDetails> allPostcards = new ArrayList<>();
		List<Order> allPostcard = new ArrayList<>();
		List<PostcardOrder> postcardOrders = findallPostcardOrder(from, to);
		for (PostcardOrder postcardOrder : postcardOrders) {
			Order getPostcard = new Order();
			List<Postcard> listPostcard = findPostcardByOrderId(Long.valueOf(postcardOrder.getOrderId()));
			if (!CollectionUtils.isEmpty(listPostcard)) {
				getPostcard.setOrderId(postcardOrder.getOrderId());
				getPostcard.setNoOfRecipients(listPostcard.size());
				getPostcard.setPrintQty(listPostcard.size());
				getPostcard.setCreatedDate(swissUtils.convertCreateDateWithHours(postcardOrder.getCreatedDate()));
				if (!StringUtils.isEmpty(postcardOrder.getSenderJson())) {
					getPostcard
							.setSenderAddress(new Gson().fromJson(postcardOrder.getSenderJson(), SenderAddress.class));
				}
				getPostcard.setStatus(postcardOrder.getOrderStatus());
				for (Postcard postcard : listPostcard) {
					PostcardDetails rResponse = new PostcardDetails();
					rResponse.setCardKey(postcard.getCardKey());
					if (postcard.getCreatedDate() != null) {
						rResponse.setCreatedDate(swissUtils.convertCreateDateWithHours(postcard.getCreatedDate()));
					}
					if (!StringUtils.isEmpty(postcard.getRecipientJson())) {
						RecipientAddress address = new Gson().fromJson(postcard.getRecipientJson(),
								RecipientAddress.class);
						//if(StringUtils.isEmpty(address.getFirstname()) && StringUtils.isEmpty(address.getLastname()))
						rResponse.setName(address.getFirstname() + " " + address.getLastname());
					}

					allPostcards.add(rResponse);
				}
				getPostcard.setRecipients(allPostcards);
			}
			allPostcard.add(getPostcard);
		}

		finalResponse.setOrders(allPostcard);
		return finalResponse;
	}

	@Override
	public List<Postcard> findPostcardByOrderId(Long orderId) {
		return query(findallPostcardByOrderidQuery, orderId, new PostcardMapper());
	}

	@Override
	public List<PostcardOrder> findallPostcardOrder(String from,String to) {
		String status ="SUBMITTED";
			return query(selectGetallPostcards, from, to, status, new PostcardOrderMapper());
		}
		

	@Override
	public List<PostcardCarouselResponse> getAllPostcardsByStatus() {
		List<PostcardCarouselResponse> allPostcards = new ArrayList<>();
		List<PostcardOrder> postcardOrders = query(selectPostcardByStatus, "DRAFT", new PostcardOrderMapper());
		for (PostcardOrder postcardOrder : postcardOrders) {
			List<Postcard> listPostcard = findPostcardByOrderId(Long.valueOf(postcardOrder.getOrderId()));
			if (!CollectionUtils.isEmpty(listPostcard)) {
					PostcardCarouselResponse postResponse = new PostcardCarouselResponse();
					postResponse.setImageId(postcardOrder.getImageId());
					postResponse.setOrderId(postcardOrder.getOrderId());
					postResponse.setOrderStatus(postcardOrder.getOrderStatus());
					postResponse.setNumberOfRecipients(String.valueOf(listPostcard.size()));
					postResponse.setCreatedDate(swissUtils.convertCreateDate(postcardOrder.getCreatedDate()));
					allPostcards.add(postResponse);
				
				
			}
		}
		return allPostcards;
	}

}
