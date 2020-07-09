package com.postcard.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.dao.PropertiesDao;
import com.postcard.model.NameValue;

@Repository
public class PropertiesDaoImpl extends BaseDao implements PropertiesDao {
	
	@Value("${updateProperty}")
	private String updatePropertySQL;
	
	@Value("${getValidationConfiguration}")
	private String getValidationConfiguration;
	
	@Override
	public void updateProperties(List<NameValue> properties) {
		for(NameValue property : properties) {
			getMainJdbcTemplate().update(updatePropertySQL, property.getValue(), property.getName());
		}
	}
	
	@Override
	public String getValidationConfig(String... key) {
		return getMainJdbcTemplate().queryForObject(getValidationConfiguration, key, String.class);
	}

}
