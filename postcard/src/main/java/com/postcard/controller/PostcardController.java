package com.postcard.controller;

import java.io.File;

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
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.postcard.model.BrandinTextRequest;
import com.postcard.model.CampaignResponse;
import com.postcard.model.PostcardResponse;
import com.postcard.model.RecipientRequest;
import com.postcard.model.SenderRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = { "Postcard API" })
public class PostcardController {

	private static final String NEWLINE = "<br/>";

	@Value("${authURL}")
	String authURL;

	@Value("${accessTokenURL}")
	String accessTokenURL;

	@Value("${clientID}")
	String clientID;

	@Value("${clientSecret}")
	String clientSecret;

	@Value("${scope}")
	String scope;
	
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
	OAuth2RestTemplate postCardRestTemplate;
	

	@GetMapping(path = "configs")
	@ApiOperation(value = "Returns all the configuration related to post card API", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponse(code = 200, message = "Configuraitons")
	public ResponseEntity<?> configurations() {
		StringBuilder sb = new StringBuilder(NEWLINE);
		sb.append(authURL).append(NEWLINE);
		sb.append(accessTokenURL).append(NEWLINE);
		sb.append(clientID).append(NEWLINE);
		sb.append(clientSecret).append(NEWLINE);
		sb.append(scope).append(NEWLINE);
		sb.append(campaignKey);
		return ResponseEntity.ok("First Service : " + sb.toString());
	}

	@GetMapping(path = "createPostcard")
	@ApiOperation(value = "Returns card key on post card API", tags = {
			"Postcard API" }, response = PostcardResponse.class)
	@ApiResponses({ @ApiResponse(code = 201, message = "Created Postcard") })
	public ResponseEntity<?> createPostcard() {
		try {
			String url = postcardBaseURL + createPostcardEndPoint + campaignKey;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<PostcardResponse> responseEntity = postCardRestTemplate.postForEntity(url, request,
					PostcardResponse.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "state")
	@ApiOperation(value = "Gets the actual state for the given postcard.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Card state") })
	public ResponseEntity<?> state() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + stateEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.getForEntity(url, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "approval")
	@ApiOperation(value = "Approve the given postcard for printing.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Card approval") })
	public ResponseEntity<?> approval() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + stateEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.postForEntity(url, request, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "sender")
	@ApiOperation(value = "Updates the sender address in the given postcard.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Update Sender") })
	public ResponseEntity<?> updateSender() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + senderAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//to be removed
			SenderRequest senderRequest = new SenderRequest("firsname","lastname","mystreet","45","11351","mycity");
			HttpEntity<SenderRequest> request = new HttpEntity<SenderRequest>(senderRequest, headers);
		    ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,request,String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "recipient")
	@ApiOperation(value = "Updates the recipient address in the given postcard.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Update Recipient") })
	public ResponseEntity<?> updateRecipient() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + recipientAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			//to be removed
			RecipientRequest recipientRequest = new RecipientRequest("firstname","lastname","new company","street","45","11351","new city", "sweden","11351");
			HttpEntity<RecipientRequest> request = new HttpEntity<RecipientRequest>(recipientRequest, headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,request, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "image")
	@ApiOperation(value = "Updates the front image in the given postcard.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Update Image") })
	public ResponseEntity<?> updateImage() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + frontImageEndPoint;
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	        FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg")); 
	        map.add("image", value);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
	        //RestTemplate restTemplate = new RestTemplate();
	        postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
	        ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "sendertext")
	@ApiOperation(value = "Updates the sender text in the given postcard.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Update Sendertext") })
	public ResponseEntity<?> updateSenderText() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + senderTextEndPoint + "Testing";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
		    //To be done
			HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, request, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "brandingtext")
	@ApiOperation(value = "Updates the branding text in the given postcard. If branding text is empty, branding text will be reset.", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Update Sendertext") })
	public ResponseEntity<?> updateBrandingText() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + brandingTextEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//to be removed
			BrandinTextRequest brandingRequest = new BrandinTextRequest("This is my brand","#FFFFFF","#FFFFFF");
			HttpEntity<BrandinTextRequest> request = new HttpEntity<BrandinTextRequest>(brandingRequest, headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT,request,String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "previews/front")
	@ApiOperation(value = "Gives a preview of the front side for the given postcard.", tags = {
			"Campaign API" }, response = CampaignResponse.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Preview Front") })
	public ResponseEntity<?> previewFront() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + frontPreviewsEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.getForEntity(url, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "previews/back")
	@ApiOperation(value = "Gives a preview of the back side for the given postcard.", tags = {
			"Campaign API" }, response = CampaignResponse.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Preview Back") })
	public ResponseEntity<?> previewBack() {
		try {
			String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + backPreviewsEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<String> responseEntity = postCardRestTemplate.getForEntity(url, String.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "branding/image")
    @ApiOperation(value = "Uploads a logo for the postcard.", tags = {
            "Postcard API" }, response = String.class)
    @ApiResponses({ @ApiResponse(code = 200, message = "Update Branding Image") })
    public ResponseEntity<?> updateBrandingImage() {
        try {
            String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + brandingImageEndPoint;
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg")); 
            map.add("image", value);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            //RestTemplate restTemplate = new RestTemplate();
            postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
            ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            return responseEntity;
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
	}
	
	   @GetMapping(path = "stamp/image")
	    @ApiOperation(value = "Updates the branding QR tag information in the given postcard.", tags = {
	            "Postcard API" }, response = String.class)
	    @ApiResponses({ @ApiResponse(code = 200, message = "Update Stamp Image") })
	    public ResponseEntity<?> updateStampImage() {
	        try {
	            String url = postcardBaseURL + postcardAPI + "2fd705a6-282c-42f8-aef9-ea729a2651e8" + stampImageEndPoint;
	            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	            FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg")); 
	            map.add("image", value);
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
	            //RestTemplate restTemplate = new RestTemplate();
	            postCardRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
	            ResponseEntity<String> responseEntity = postCardRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
	            return responseEntity;
	        } catch (Exception e) {
	            System.out.println(e);
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	        
	    }

}
