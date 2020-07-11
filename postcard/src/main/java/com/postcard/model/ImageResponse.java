package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class ImageResponse {

	private String cardKey;
	private List<Error> errors;
	private List<Warning> warnings;
	
}
