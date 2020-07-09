package com.postcard.validator;

public interface Validator {

	boolean isValid(String content);

	String errorMessage();
}
