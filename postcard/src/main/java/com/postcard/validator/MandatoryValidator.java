package com.postcard.validator;

import io.micrometer.core.instrument.util.StringUtils;

public class MandatoryValidator extends BaseValidator {

	@Override
	public boolean isValid(String content) {
		return StringUtils.isNotBlank(content);
	}

}
