package com.postcard.dao;

import java.util.List;

import com.postcard.model.NameValue;

public interface PropertiesDao {

	void updateProperties(List<NameValue> properties);

	String getValidationConfig(String... key);

}
