package com.postcard.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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

import com.google.gson.Gson;
import com.postcard.dao.PostcardDao;
import com.postcard.dao.PostcardOrderDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.ApprovalResponse;
import com.postcard.model.BrandingImageResponse;
import com.postcard.model.BrandingStampResponse;
import com.postcard.model.BrandingTextResponse;
import com.postcard.model.GetAllPostcard;
import com.postcard.model.Image;
import com.postcard.model.ImageResponse;
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
	public PostcardOrder submitOrder(OAuth2RestTemplate postCardRestTemplate, long orderId, String cardKey) throws ServiceException {
		PostcardOrder postcardOrder = postcardOrderDao.findOne(orderId);
		// Write a query to find all the postcards for given orderID
		Postcard postcard = postcardDao.findOneByCardKey(cardKey);
		// Update post card
		approvePostcard(postCardRestTemplate, postcard);
		getPostcardState(postCardRestTemplate, postcard);
		//
		return postcardOrder;
	}

	@Override
	public void updateRecipient(OAuth2RestTemplate postCardRestTemplate, String postcardKey, Postcard postcard) {

		try {
			String url = postcardBaseURL + postcardAPI + postcardKey + recipientAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(postcard.getRecipientJson(), headers);
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
			//FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg"));
			Path tempFile = Files.createTempFile("upload-test-file", ".jpg");
		    Files.write(tempFile, image.getImage());
		    System.out.println("uploading: " + tempFile);
		    File file = tempFile.toFile();
		    //to upload in-memory bytes use ByteArrayResource instead
		    FileSystemResource value = new FileSystemResource(file);
			 
			map.add("image", value);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
			// RestTemplate restTemplate = new RestTemplate();
			postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
			ResponseEntity<ImageResponse> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,
					requestEntity, ImageResponse.class);
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
	public ResponseEntity<?> approvePostcard(OAuth2RestTemplate postCardRestTemplate, Postcard postcard) {
		try {
			String url = postcardBaseURL + postcardAPI + postcard.getCardKey() + approvalEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<ApprovalResponse> responseEntity = postCardRestTemplate.postForEntity(url, request, ApprovalResponse.class);
			System.out.println(responseEntity.getBody());
			//update approval response
			postcard.setSubmissionStatus("Submitted");
			postcard.setResponse(new Gson().toJson(responseEntity.getBody()));
			if(postcard.getAttempts()!= null) {
				int attempts = postcard.getAttempts();
				postcard.setAttempts(++attempts);
			}
			postcardDao.updatePostcardApproval(postcard);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@Override
	public ResponseEntity<?> getPostcardState(OAuth2RestTemplate postCardRestTemplate, Postcard postcard) {
		try {
			String url = postcardBaseURL + postcardAPI + postcard.getCardKey() + stateEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<StateResponse> responseEntity = postCardRestTemplate.getForEntity(url, StateResponse.class);
			postcard.setCardStatus(responseEntity.getBody().getState().getState());
			postcardDao.updatePostcardState(postcard);
			System.out.println(responseEntity.getBody());
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
