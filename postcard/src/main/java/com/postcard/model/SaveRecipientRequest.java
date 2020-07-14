package com.postcard.model;

import lombok.Data;

@Data
public class SaveRecipientRequest {
	
	private Integer orderId;
	private RecipientAddress recipient;
	

}
