package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class PreviewFrontResponse {

	private String cardKey;
	private String side;
	private List<Error> errors;
}
