package com.postcard.model;

import lombok.Data;

@Data
public class UpdateSenderAddressRequest {

	private Integer orderId;
	private SenderAddress senderAddress;
}
