package com.postcard.model;

import lombok.Data;

@Data
public class UpdateSenderRequest {

	private Integer orderId;
	private SenderAddress senderAddress;
	private String senderText;
}
