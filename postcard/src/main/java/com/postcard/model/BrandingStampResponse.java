package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class BrandingStampResponse {
	private String cardKey;
	private List<Error> errors;
	private List<Warning> warnings;
}
