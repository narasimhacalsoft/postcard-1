package com.postcard.model;

import lombok.Data;

@Data
public class UpdateSenderTextRequest {
	
	private Integer orderId;
	private String senderText;

}
