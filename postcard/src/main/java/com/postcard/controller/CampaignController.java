package com.postcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.postcard.model.CampaignResponse;
import com.postcard.model.PostcardResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = { "Campaign API" })
public class CampaignController {

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

	@Value("${postcardAPI}")
	String postcardAPI;

	@Value("${campaignKey}")
	String campaignKey;

	@Value("${createPostcardEndPoint}")
	String createPostcardEndPoint;
	
	@Value("${campaignsEndPoint}")
	String campaignsEndPoint;
	
	@Value("${statisticEndpoint}")
	String statisticEndpoint;
	
	@Value("${postcardBaseURL}")
	String postcardBaseURL;

	@Autowired
	OAuth2RestTemplate postCardRestTemplate;

	@GetMapping(path = "campaigns")
	@ApiOperation(value = "Gets the actual statistic of the given campaign.", tags = {
			"Campaign API" }, response = CampaignResponse.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Campaign Statistic") })
	public ResponseEntity<?> campaigns() {
		try {
			String url = postcardBaseURL + campaignsEndPoint + campaignKey + statisticEndpoint;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//HttpEntity<String> request = new HttpEntity<String>("{}", headers);
			ResponseEntity<CampaignResponse> responseEntity = postCardRestTemplate.getForEntity(url, CampaignResponse.class);
			return responseEntity;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
