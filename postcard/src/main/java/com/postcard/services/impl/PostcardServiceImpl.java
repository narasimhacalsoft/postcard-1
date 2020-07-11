package com.postcard.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.postcard.dao.PostcardDao;
import com.postcard.dao.PostcardOrderDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardOrder;
import com.postcard.model.PostcardResponse;
import com.postcard.model.RecipientAddress;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.model.SenderAddress;
import com.postcard.model.SubmitOrderResponse;
import com.postcard.service.PostcardOrderService;
import com.postcard.service.PostcardService;

@Service
public class PostcardServiceImpl implements PostcardService {

	@Value("${postcardBaseURL}")
	String postcardBaseURL;

	@Value("${postcardAPI}")
	String postcardAPI;

	@Value("${campaignKey}")
	String campaignKey;

	@Value("${createPostcardEndPoint}")
	String createPostcardEndPoint;

	@Value("${stateEndPoint}")
	String stateEndPoint;

	@Value("${approvalEndPoint}")
	String approvalEndPoint;

	@Value("${senderAddressEndPoint}")
	String senderAddressEndPoint;

	@Value("${recipientAddressEndPoint}")
	String recipientAddressEndPoint;

	@Value("${frontImageEndPoint}")
	String frontImageEndPoint;

	@Value("${senderTextEndPoint}")
	String senderTextEndPoint;

	@Value("${brandingTextEndPoint}")
	String brandingTextEndPoint;

	@Value("${frontPreviewsEndPoint}")
	String frontPreviewsEndPoint;

	@Value("${backPreviewsEndPoint}")
	String backPreviewsEndPoint;

	@Value("${brandingImageEndPoint}")
	String brandingImageEndPoint;

	@Value("${stampImageEndPoint}")
	String stampImageEndPoint;

	@Autowired
	PostcardDao postcardDao;

	@Autowired
	PostcardOrderDao postcardOrderDao;

	@Autowired
	PostcardOrderService postcardOrderService;

	public Long createPostcard(final Postcard postcard) {
		postcardDao.createPostcard(postcard);
		return null;
	}

	public Long updatePostcard(Postcard postcard) {
		postcardDao.updatePostcard(postcard);
		return null;
	}

	public List<Postcard> findallPostcard() {
		List<Postcard> postcards = postcardDao.findallPostcard();
		return postcards;
	}

	public Postcard findOne(Long card_id) {
		Postcard postcard = postcardDao.findOne(card_id);
		return postcard;
	}

	public void deletePostcard(Postcard postcard) {
		postcardDao.deletePostcard(postcard);
	}

	@Override
	public SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException {
		for (RecipientAddress address : request.getRecipients()) {
			if (!CollectionUtils.isEmpty(address.getErrors())) {
				throw new ServiceException("Invalid Recipient Address");
			}
		}
		return postcardDao.saveRecipientAddress(request);
	}
	
	@Override
	public void updatePostcardkey(Postcard postcard) {
		postcardDao.updatePostcardkey(postcard);
		
	}


	@Override
	public SubmitOrderResponse submitOrder(OAuth2RestTemplate postCardRestTemplate, long orderId)
			throws ServiceException {
		PostcardOrder postcardOrder = postcardOrderDao.findOne(orderId);

		// Write a query to find all the postcards for given orderID
		List<Postcard> postcardOrderList = postcardDao.findallPostcard();
		for (Postcard postcard : postcardOrderList) {
			HttpHeaders headers = new HttpHeaders();
			// Create post card
			String url = postcardBaseURL + createPostcardEndPoint + campaignKey;
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<PostcardResponse> responseEntity = postCardRestTemplate.postForEntity(url, request,
					PostcardResponse.class);
			// update postcard order wiht card key
			postcard.setCardKey(responseEntity.getBody().getCardKey());
			updatePostcardkey(postcard);

			// Update sender info
			String senderAddress = postcardBaseURL + postcardAPI + postcard.getCardKey() + senderAddressEndPoint;
			//HttpHeaders headers = new HttpHeaders();
			//headers.setContentType(MediaType.APPLICATION_JSON);
			// to be removed
			
			 SenderAddress senderRequest = new SenderAddress("firsname", "lastname", "mystreet", "45", "11351", "mycity");
			 
			//SenderAddress senderRequest = new Gson().fromJson(postcardOrder.getSenderJson(), SenderAddress.class);
			headers.clear(); 
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<SenderAddress> senderEntity = new HttpEntity<SenderAddress>(senderRequest, headers);
			ResponseEntity<SenderAddress> addressResponse = postCardRestTemplate.exchange(url, HttpMethod.PUT, senderEntity, SenderAddress.class);
			// Update the sender address info

			//

		}

		return null;
	}

	private void getHttpEntity() {

	}


	@Override
	public List<GetAllPostcard> getAllPostcards() {
		return postcardDao.getAllPostcards();
	}

}
