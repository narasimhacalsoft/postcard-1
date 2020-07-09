package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class SaveRecipientRequest {
	
	private Integer orderId;
	private List<RecipientAddress> recipients;
	

}
