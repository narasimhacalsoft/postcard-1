package com.postcard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveRecipientResponse {
	
	private Integer orderId;
	private Postcard postcard;
	
}
