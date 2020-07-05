package com.postcard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDao {

	@Autowired
	JdbcTemplate mainJdbcTemplate;
	
}
