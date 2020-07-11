package com.postcard.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class DatabasePropertiesPostProcessor implements EnvironmentPostProcessor {

	// Logger
	
	private static final String PROPERTIES_QUERY = "select name,value from PROPERTIES";
	
	private static final String IMAGE_QUERY = "insert into image(image) values (?)";

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
			File file = new ClassPathResource("static/mi-pham-223464.jpg", this.getClass().getClassLoader()).getFile();
			KeyHolder holder = new GeneratedKeyHolder();
			// File file = new File("C://mi-pham-223464.jpg");
			FileInputStream input = new FileInputStream(file);
			getJdbcTemplate(environment).update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(IMAGE_QUERY,
							Statement.RETURN_GENERATED_KEYS);
					ps.setBlob(1, input);
					return ps;
				}
			}, holder);
			
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
