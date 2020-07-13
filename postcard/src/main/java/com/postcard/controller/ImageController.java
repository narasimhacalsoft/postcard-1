package com.postcard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.postcard.model.Image;
import com.postcard.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Controller
@Api(tags = { "Image API" })
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
			"Image API" }, authorizations = { @Authorization(value="jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Getall Image") })
	public ResponseEntity<?> getAllImage() {
		try {
			List<Image> images= imageService.findallImage();
			return ResponseEntity.ok(images);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping(path = "uploadStamp", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Uploads stamp for the postcard", tags = {
			"Image API" }, authorizations = { @Authorization(value="jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Stamp Uploaded") })
	public ResponseEntity<?> uploadStamp(@RequestParam("file") MultipartFile file, @RequestParam("orderId") long orderId) {
		try {
			Image image = new Image();
			image.setImage(file.getBytes());
			String imageId = imageService.createImage(file,orderId, "stamp");
			return ResponseEntity.ok(imageId);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
