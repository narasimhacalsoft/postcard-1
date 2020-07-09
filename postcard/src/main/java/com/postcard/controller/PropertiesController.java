package com.postcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.postcard.dao.PropertiesDao;
import com.postcard.model.PropertiesUpdateRequest;

@RestController
public class PropertiesController {

	@Autowired
	private RestartEndpoint restartEndpoint;

	@Autowired
	PropertiesDao propertiesDao;

	@GetMapping(path = "/restart")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> refresh() {
		restartEndpoint.restart();
		return ResponseEntity.ok("Context restarted..");
	}

	@PostMapping(path = "/updateProperties")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody PropertiesUpdateRequest request) {
		propertiesDao.updateProperties(request.getNameValues());
		return ResponseEntity.ok("Context restarted..");
	}

}
