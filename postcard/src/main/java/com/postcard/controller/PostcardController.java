package com.postcard.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.postcard.model.PreviewBackResponse;
import com.postcard.model.PreviewFrontResponse;
import com.postcard.model.RecipientAddress;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.service.ImageService;
import com.postcard.service.PostcardOrderService;
import com.postcard.service.PostcardService;
import com.postcard.validator.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.apachecommons.CommonsLog;

@Controller
@CrossOrigin
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
	Map<String, List<Validator>> recipientValidationContext;

	// @GetMapping(path = "configs")
	/*
	 * @ApiOperation(value =
	 * "Returns all the configuration related to post card API", tags = {
	 * "Postcard API" }, response = String.class)
	 * 
	 * @ApiResponse(code = 200, message = "Configuraitons")
	 */
	public ResponseEntity<?> configurations() {
		// log.info("In Configurations");
		StringBuilder sb = new StringBuilder(NEWLINE);
		sb.append(authURL).append(NEWLINE);
		sb.append(accessTokenURL).append(NEWLINE);
		sb.append(clientID).append(NEWLINE);
		sb.append(clientSecret).append(NEWLINE);
		sb.append(scope).append(NEWLINE);
		sb.append(campaignKey);
		return ResponseEntity.ok("First Service : " + sb.toString());
	}

	@PostMapping(path = "validateRecipients", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Validate upload recipient address file and validate Mandatory and Maximumn lenght", tags = {
			"Postcard API" }, authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "validate CSV file") })
	public ResponseEntity<?> validate(@RequestParam("file") MultipartFile file) {
		try {
			List<RecipientAddress> listAddress = new ArrayList<>();
			JSONObject json = null;
			final String delimiter = ",";
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(file.getBytes()), "UTF-8"));
			String line = "";
			String[] header = null;
			String[] data = null;
			int iteration = 0;
			while ((line = br.readLine()) != null) {
				if (iteration == 0) {
					iteration++;
					header = line.split(delimiter);
					continue;
				}

				data = line.split(delimiter);
				json = new JSONObject();
				List<JSONObject> errors = new ArrayList<>();
				for (int i = 0; i < data.length; i++) {
					String field = header[i];
					String value = data[i];
					json.put(field, data[i]);
					List<Validator> validators = recipientValidationContext.get(field);
					for (Validator validator : validators) {
						if (!validator.isValid(value)) {
							JSONObject error= new JSONObject();
							error.put("code", validator.errorMessage().getCode());
							error.put("description", validator.errorMessage().getDescription());
							errors.add(error);
							break;
						}
					}

					json.put("errors", errors);

				}
				listAddress.add(new Gson().fromJson(json.toString(), RecipientAddress.class));
			}
			return ResponseEntity.ok(listAddress);
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping(path = "createRecipinet")
	@ApiOperation(value = "Save Receipient Address Details", tags = { "Postcard API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Recipient details saved Successfully") })
	public ResponseEntity<?> saveRecipients(@RequestBody SaveRecipientRequest request) {
		try {
			return ResponseEntity.ok(postcardService.saveRecipientAddress(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping(path = "previews/front")
	@ApiOperation(value = "Gets the previews front", tags = { "Postcard API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Preview Front Image") })
	public ResponseEntity<?> previewFront(@RequestParam String cardKey) {
		try {
			String url = postcardBaseURL + postcardAPI + cardKey + frontPreviewsEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// HttpEntity<String> request = new HttpEntity<String>("{}",
			// headers);
			ResponseEntity<PreviewFrontResponse> responseEntity = postCardRestTemplate.getForEntity(url,
					PreviewFrontResponse.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping(path = "previews/back")
	@ApiOperation(value = "Gets the previews Back", tags = { "Postcard API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Preview Back Image") })
	public ResponseEntity<?> previewBack(@RequestParam String cardKey) {
		try {
			String url = postcardBaseURL + postcardAPI + cardKey + backPreviewsEndPoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// HttpEntity<String> request = new HttpEntity<String>("{}",
			// headers);
			ResponseEntity<PreviewBackResponse> responseEntity = postCardRestTemplate.getForEntity(url,
					PreviewBackResponse.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping(path = "getAllPostcards")
	@ApiOperation(value = "Get all the Postcard and PostcardOrder Details", tags = {
			"Postcard API" }, authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Get the PostCard and PostcardOrder details") })
	public ResponseEntity<?> getAllPostcards(@RequestParam(value = "from", required = true) String from,@RequestParam(value = "to", required = true) String to) {
		try {
			return ResponseEntity.ok(postcardService.getAllPostcards(from,to,null));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@GetMapping(path = "getAllPostcardByStatus")
	@ApiOperation(value = "Get all the Postcard and PostcardOrder Details by status", tags = {
			"Postcard API" }, authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Get the PostCard and PostcardOrder details by status") })
	public ResponseEntity<?> getAllPostcardsByStatus(@RequestParam(value = "status", required = true) String status) {
		try {
			return ResponseEntity.ok(postcardService.getAllPostcards(null,null,status));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}


	@PostMapping(path = "submitOrder")
	@ApiOperation(value = "Submit the Postcard order", tags = { "Postcard API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Postcard submitted Successfully") })
	public ResponseEntity<?> createPostcardOrder(@RequestParam(value = "orderId", required = true) Long orderId, @RequestParam(value = "cardKey", required = true) String cardKey) {
		try {
			// create a post card

			return ResponseEntity.ok(postcardService.submitOrder(postCardRestTemplate, orderId, cardKey));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
