package com.postcard.validator;

import com.postcard.model.Error;

public interface Validator {

	boolean isValid(String content);

	Error errorMessage();
}
