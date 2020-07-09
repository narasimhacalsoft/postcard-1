package com.postcard.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class DatabasePropertiesPostProcessor implements EnvironmentPostProcessor {

	// Logger
	
	private static final String PROPERTIES_QUERY = "select name,value from PROPERTIES";

	private static final String PROPERTY_SOURCE_NAME = "databaseProperties";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		try {
			Map<String, Object> props = getJdbcTemplate(environment).query(PROPERTIES_QUERY,
					new ResultSetExtractor<Map<String, Object>>() {

						@Override
						public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
							Map<String, Object> properties = new HashMap<>();
							while (rs.next()) {
								properties.put(rs.getString("name"), rs.getString("value"));
							}
							return properties;
						}
					});
			environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, props));
		} catch (Exception e) {
			log.error("Unable to fetch properties", e);
		}
	}
	
	
	public JdbcTemplate getJdbcTemplate(Environment environment) {
		DataSource ds = DataSourceBuilder
        .create()
        .username(environment.getProperty("spring.datasource.username"))
        .password(environment.getProperty("spring.datasource.password"))
        .url(environment.getProperty("spring.datasource.url"))
        .driverClassName(environment.getProperty("spring.datasource.driverClassName"))
        .build();
		return new JdbcTemplate(ds);
	}

}
