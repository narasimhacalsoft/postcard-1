package com.postcard.validator;

public abstract class BaseValidator implements Validator {
	
	String message;

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
