package com.postcard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import com.google.gson.Gson;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.postcard.model.BrandingText;
import com.postcard.model.CampaignResponse;
import com.postcard.model.Image;
import com.postcard.model.PostcardOrder;
import com.postcard.model.Postcard;
import com.postcard.model.PostcardResponse;
import com.postcard.model.RecipientAddress;
import com.postcard.model.RecipientAddressRequest;
import com.postcard.model.SenderAddress;
import com.postcard.model.UpdateSenderRequest;
import com.postcard.service.ImageService;
import com.postcard.service.PostcardOrderService;
import com.postcard.service.PostcardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.apachecommons.CommonsLog;

import com.postcard.validator.ValidationContext;
import com.postcard.validator.Validator;

@Controller
@Api(tags = { "Postcard API" })
@CommonsLog
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
	PostcardService postcardService;
	
	@Autowired
    PostcardOrderService orderService;
	
	@Autowired
    ImageService imageService;
	

	@Autowired
	OAuth2RestTemplate postCardRestTemplate;
	
	@Autowired
    ValidationContext recipientValidationContext;

	@GetMapping(path = "configs")
	@ApiOperation(value = "Returns all the configuration related to post card API", tags = {
			"Postcard API" }, response = String.class)
	@ApiResponse(code = 200, message = "Configuraitons")
	public ResponseEntity<?> configurations() {
	    //log.info("In Configurations");
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + stateEndPoint;
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + approvalEndPoint;
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + senderAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//to be removed
			SenderAddress senderRequest = new SenderAddress("firsname","lastname","mystreet","45","11351","mycity");
			HttpEntity<SenderAddress> request = new HttpEntity<SenderAddress>(senderRequest, headers);
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + recipientAddressEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			//to be removed
			RecipientAddressRequest recipientRequest = new RecipientAddressRequest("title", "firstname","lastname","new company","street","45","11351","new city", "sweden","11351");
			HttpEntity<RecipientAddressRequest> request = new HttpEntity<RecipientAddressRequest>(recipientRequest, headers);
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + frontImageEndPoint;
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + senderTextEndPoint + "Testing";
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + brandingTextEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//to be removed
			BrandingText brandingRequest = new BrandingText("This is my brand","#FFFFFF","#FFFFFF");
			HttpEntity<BrandingText> request = new HttpEntity<BrandingText>(brandingRequest, headers);
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + frontPreviewsEndPoint;
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
			String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + backPreviewsEndPoint;
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
            String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + brandingImageEndPoint;
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
	
	@GetMapping(path = "branding/stamp")
    @ApiOperation(value = "Uploads a logo for the postcard.", tags = {
            "Postcard API" }, response = String.class)
    @ApiResponses({ @ApiResponse(code = 200, message = "Update Stamp Image") })
    public ResponseEntity<?> updateStampImage() {
        try {
            String url = postcardBaseURL + postcardAPI + "6f504948-167f-4c33-8366-cd2ac57b15a5" + "/branding/stamp";
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            FileSystemResource value = new FileSystemResource(new File("C://mi-pham-223464.jpg")); 
            map.add("stamp", value);
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
	
   @PostMapping(path = "validateRecipients")
   public ResponseEntity<?> validate() {
        try {
            String[] header = "title,firstname,lastname".split(",");
            String[] data = "test,Beniton, ".split(",");
            JSONObject json = new JSONObject();
            List<String> errors = new ArrayList<>();
            for(int i=0; i < data.length;i++) {
                String field = header[i];
                String value = data[i];
                json.put(field, value);
                List<Validator> validators = recipientValidationContext.getValidators(field);
                for(Validator validator : validators) {
                    if(!validator.isValid(value)) {
                        errors.add(validator.errorMessage());
                        break;
                    }
                }
            }
            json.put("errors", errors);
            return ResponseEntity.ok(new Gson().fromJson(json.toString(), RecipientAddress.class));
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }  

}
