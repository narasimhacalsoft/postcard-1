package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class RecipientResponse {

	private String cardKey;
	private String successMessage;
	private List<Error> errors;
	private List<Warning> warnings;
}
