package com.postcard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.postcard.model.CampaignResponse;
import com.postcard.model.Image;
import com.postcard.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Controller
@Api(tags = { "Campaign API" })
public class ImageController {

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
	ImageService imageService;

	@GetMapping(path = "getAllImage", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets the static images for selecting.", tags = {
			"Postcard API" }, authorizations = { @Authorization(value="jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Getall Image") })
	public ResponseEntity<?> campaigns() {
		try {
			List<Image> images= imageService.findallImage();
			return ResponseEntity.ok(images);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
