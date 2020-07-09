package com.postcard.configuration;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.postcard.dao.PropertiesDao;
import com.postcard.validator.MandatoryValidator;
import com.postcard.validator.MaxlengthValidator;
import com.postcard.validator.ValidationContext;
import com.postcard.validator.Validator;

import lombok.extern.apachecommons.CommonsLog;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableOAuth2Client
@CommonsLog
public class Configurations extends WebMvcConfigurationSupport {

	@Value("${authURL}")
	String authURL;

	@Value("${accessTokenURL}")
	String accessTokenURL;

	@Value("${clientID}")
	String clientID;

	@Value("${clientSecret}")
	String clientSecret;

	@Value("${scope}")
	String scope;

	@Autowired
	PropertiesDao propertiesDao;

	@Bean
	@ConditionalOnProperty(name = "enable-swagger", havingValue = "Y")
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonDateFormatter() {
		return builder -> {
			builder.simpleDateFormat("MM-dd-yyyy HH:mm:ss");
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")));
		};
	}

	@Bean
	public OAuth2ProtectedResourceDetails tokenProvider() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setClientId(clientID);
		details.setClientSecret(clientSecret);
		details.setAccessTokenUri(accessTokenURL);
		details.setTokenName("access_token");
		details.setScope(Arrays.asList(scope));
		details.setGrantType("client_credentials");
		return details;
	}

	@Bean
	public OAuth2RestTemplate postCardRestTemplate(OAuth2ClientContext clientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(tokenProvider(), clientContext);
		template.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
		return template;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@SuppressWarnings("unchecked")
	public ValidationContext recipientValidationContext() {
		ValidationContext context = new ValidationContext();
		try {
			String validationConfiguration = propertiesDao.getValidationConfig("recipientAddress");
			JSONObject json = new JSONObject(validationConfiguration);
			Iterator<String> keys = json.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if (json.get(key) instanceof JSONObject) {
					JSONObject valueObject = (JSONObject) json.get(key);
					JSONArray validations = valueObject.getJSONArray("validations");
					if(validations != null) {
						List<Validator> validators = new ArrayList<>();
						for(int i=0; i<validations.length(); i++) {
							JSONObject validation = (JSONObject)validations.get(i);
							String type = String.valueOf(validation.get("type"));
							
							switch (type) {
							case "MAX_LENGTH":
								validators.add(new Gson().fromJson(validation.toString(), MaxlengthValidator.class));
								break;
							case "MANDATORY":
								validators.add(new Gson().fromJson(validation.toString(), MandatoryValidator.class));
								break;

							default:
								break;
							}
						}
						if(!CollectionUtils.isEmpty(validators)) {
							context.put(key, validators);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Unable to initialize validation context", e);
		}
		return context;
	}
}
