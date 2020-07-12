package com.postcard.services.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;

import com.postcard.dao.PostcardDao;
import com.postcard.dao.PostcardOrderDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.ApprovalResponse;
import com.postcard.model.BrandingImageResponse;
import com.postcard.model.BrandingStampResponse;
import com.postcard.model.BrandingTextResponse;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Image;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardOrder;
import com.postcard.model.PostcardResponse;
import com.postcard.model.RecipientAddress;
import com.postcard.model.RecipientResponse;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.model.SenderResponse;
import com.postcard.model.SenderTextResponse;
import com.postcard.model.StateResponse;
import com.postcard.service.ImageService;
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

	@Autowired
	ImageService imageService;

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
	
	public List<Postcard> findallPostcardByOrderId( long orderId) {
		List<Postcard> postcards = postcardDao.findPostcardByOrderId(orderId);
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
	public PostcardOrder submitOrder(OAuth2RestTemplate postCardRestTemplate, long orderId) throws ServiceException {
		PostcardOrder postcardOrder = postcardOrderDao.findOne(orderId);

		// Write a query to find all the postcards for given orderID
		List<Postcard> postcardOrderList = postcardDao.findPostcardByOrderId(orderId);
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

			// Update post card
			updateSender(postCardRestTemplate, postcard.getCardKey(), postcardOrder);
			updateSenderText(postCardRestTemplate, postcard.getCardKey(), postcardOrder);
			updateBrandingText(postCardRestTemplate, postcard.getCardKey(), postcardOrder);
			updateRecipient(postCardRestTemplate, postcard.getCardKey(), postcard);
			updateImage(postCardRestTemplate, postcard.getCardKey(), postcardOrder);
			updateBrandingImage(postCardRestTemplate, postcard.getCardKey(), postcard);
			updateStampImage(postCardRestTemplate, postcard.getCardKey(), postcard);
			approvePostcard(postCardRestTemplate, postcard.getCardKey(), postcard);
			
			getPostcardState(postCardRestTemplate, postcard.getCardKey(), postcardOrder);
			

			//

		}

		return postcardOrder;
	}

	@Override
	public void updateRecipient(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard) {

		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + recipientAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// to be removed
			// RecipientAddressRequest recipientRequest = new
			// RecipientAddressRequest("title", "firstname","lastname","new
			// company","street","45","11351","new city", "sweden","11351");
			HttpEntity<String> request = new HttpEntity<String>(postcard.getRecipientJson(), headers);
			// HttpEntity<RecipientAddressRequest> request = new
			// HttpEntity<RecipientAddressRequest>(recipientRequest, headers);
			ResponseEntity<RecipientResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,
					request, RecipientResponse.class);
			System.out.println(responseEntity.getBody());
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public List<GetAllPostcard> getAllPostcards() {
		return postcardDao.getAllPostcards();
	}

	@Override
	public void updateSender(OAuth2RestTemplate postCardRestTemplate, String postcardKey, PostcardOrder postcardOrder) {
		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + senderAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// to be removed
			// SenderAddress senderRequest = new
			// SenderAddress("firsname","lastname","mystreet","45","11351","mycity");
			HttpEntity<String> request = new HttpEntity<String>(postcardOrder.getSenderJson(), headers);
			ResponseEntity<SenderResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, request,
					SenderResponse.class);
			System.out.println(responseEntity.getBody());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void updateSenderText(OAuth2RestTemplate postCardRestTemplate, String postcardKey,
			PostcardOrder postcardOrder) {
		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + senderTextEndPoint
					+ postcardOrder.getSenderText();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// To be done
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<SenderTextResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,
					request, SenderTextResponse.class);
			System.out.println(responseEntity.getBody());
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void updateBrandingText(OAuth2RestTemplate postCardRestTemplate, String postcardKey,
			PostcardOrder postcardOrder) {
		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + brandingTextEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// to be removed
			// BrandingText brandingRequest = new BrandingText("This is my
			// brand","#FFFFFF","#FFFFFF");
			HttpEntity<String> request = new HttpEntity<String>(postcardOrder.getBrandingText(), headers);
			ResponseEntity<BrandingTextResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,
					request, BrandingTextResponse.class);
			System.out.println(responseEntity.getBody());

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	@Override
	public void updateImage(OAuth2RestTemplate postCardRestTemplate, String postcardKey, PostcardOrder postcardOrder) {
		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + frontImageEndPoint;
			Image image = imageService.findOne((long) postcardOrder.getImageId().intValue());
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg"));
			/*
			 * ByteArrayInputStream bais = new
			 * ByteArrayInputStream(image.getImage()); final BufferedImage
			 * buffImage = ImageIO.read(bais);
			 */
			map.add("image", value);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
			// RestTemplate restTemplate = new RestTemplate();
			postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
			ResponseEntity<BrandingImageResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,
					requestEntity, BrandingImageResponse.class);
			System.out.println(responseEntity.getBody());

		} catch (Exception e) {
			System.out.println(e);

		}

	}
	
	@Override
	public void updateStampImage(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard) {
		try {
            String url = postcardBaseURL + postcardAPI + postcardKey + stampImageEndPoint;
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg")); 
            map.add("stamp", value);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            //RestTemplate restTemplate = new RestTemplate();
            postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
            ResponseEntity<BrandingStampResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, BrandingStampResponse.class);
            System.out.println(responseEntity.getBody());
        } catch (Exception e) {
            System.out.println(e);
        }
	}

	@Override
	public void updateBrandingImage(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard) {
		try {
            String url = postcardBaseURL + postcardAPI + postcardKey + brandingImageEndPoint;
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg")); 
            map.add("image", value);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            //RestTemplate restTemplate = new RestTemplate();
            postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
            ResponseEntity<BrandingImageResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, BrandingImageResponse.class);
            System.out.println(responseEntity.getBody());
        } catch (Exception e) {
            System.out.println(e);
        }
	}

	@Override
	public void approvePostcard(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard) {
		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + approvalEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<ApprovalResponse> responseEntity = postCardRestTemplate.postForEntity(url, request, ApprovalResponse.class);
			System.out.println(responseEntity.getBody());
			//Update postcard response
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	@Override
	public ResponseEntity<?> getPostcardState(OAuth2RestTemplate postCardRestTemplate, String postcardKey,
			PostcardOrder postcardOrder) {
		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + stateEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<StateResponse> responseEntity = postCardRestTemplate.getForEntity(url, StateResponse.class);
			//update the state of the postcard
			System.out.println(responseEntity.getBody());
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
