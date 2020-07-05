package com.postcard.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfiguration {

	@Value("${spring.datasource.driverClassName}")
	private String DB_DRIVER;

	@Value("${spring.datasource.password}")
	private String DB_PASSWORD;

	@Value("${spring.datasource.url}")
	private String DB_URL;

	@Value("${spring.datasource.username}")
	private String DB_USERNAME;

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.driverClassName(DB_DRIVER)
				.url(DB_URL)
				.username(DB_USERNAME)
				.password(DB_PASSWORD)
				.build();
	}
	
	@Bean("mainJdbcTemplate")
	@DependsOn("dataSource")
	public JdbcTemplate mainJdbcTemplate(@Qualifier("dataSource") DataSource mainDataSource) {
		return new JdbcTemplate(mainDataSource);
	}

}
