package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class PreviewBackResponse {
	
	private String cardKey;
	private String side;
	private List<Error> errors;

}
