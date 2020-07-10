package com.postcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.postcard.model.UpdateBrandRequest;
import com.postcard.model.UpdateSenderRequest;
import com.postcard.service.PostcardOrderService;

import io.swagger.annotations.Api;
import lombok.extern.apachecommons.CommonsLog;

@Controller
@Api(tags = { "PostcardOrder API" })
@CommonsLog
public class PostcardOrderController {
	
	@Autowired
    PostcardOrderService orderService;
	
	@PostMapping(path = "updateSenderInfo")
	public ResponseEntity<?> updateSenderInfo(@RequestBody UpdateSenderRequest request) {
		try {
			return ResponseEntity.ok(orderService.updateSenderAddress(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@PostMapping(path = "updateBrandInfo")
	public ResponseEntity<?> updateBrandInfo(@RequestBody UpdateBrandRequest request) {
		try {
			return ResponseEntity.ok(orderService.updateBrandInfo(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
