package com.postcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderRequest;
import com.postcard.service.PostcardOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.apachecommons.CommonsLog;

@Controller
@Api(tags = { "PostcardOrder API" })
@CommonsLog
public class PostcardOrderController {

	@Autowired
	PostcardOrderService orderService;

	@PostMapping(path = "updateSenderInfo")
	@ApiOperation(value = "update Sender information", tags = { "PostcardOrder API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Update sender information Successfully") })
	public ResponseEntity<?> updateSenderInfo(@RequestBody UpdateSenderRequest request) {
		try {
			return ResponseEntity.ok(orderService.updateSenderAddress(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping(path = "updateBrandInfo")
	@ApiOperation(value = "update Branding information", tags = { "PostcardOrder API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Update Branding information Successfully") })
	public ResponseEntity<?> updateBrandInfo(@RequestBody UpdateBrandRequest request) {
		try {
			return ResponseEntity.ok(orderService.updateBrandInfo(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping(path = "createPostcardOrder")
	@ApiOperation(value = "Create Postcard Order", tags = { "PostcardOrder API" }, authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses({ @ApiResponse(code = 200, message = "Postcard Created Successfully") })
	public ResponseEntity<?> createPostcardOrder() {
		try {
			return ResponseEntity.ok(orderService.createPostcardOrder());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
