package com.postcard.model;

import lombok.Data;

@Data
public class UpdateBrandRequest {
	
	private Integer orderId;
	private BrandingText brandingText;
	private String brandText;

}
