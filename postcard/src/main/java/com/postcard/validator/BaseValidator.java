package com.postcard.validator;

import com.postcard.model.Error;

public abstract class BaseValidator implements Validator {
	
	Error message;

	@Override
	public Error errorMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
