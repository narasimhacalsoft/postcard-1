package com.postcard.validator;

import java.util.HashMap;
import java.util.List;

public class ValidationContext extends HashMap<String, List<Validator>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6737713865785110192L;

	public List<Validator> getValidators(String field){
		return get(field);
	}
}
