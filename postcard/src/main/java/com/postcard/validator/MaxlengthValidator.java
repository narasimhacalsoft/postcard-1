package com.postcard.validator;

import io.micrometer.core.instrument.util.StringUtils;

public class MaxlengthValidator extends BaseValidator {
	
	private Integer value;

	@Override
	public boolean isValid(String content) {
		if(StringUtils.isBlank(content)) {
			return true;
		}
		return content.length() <= value;
	}

}
