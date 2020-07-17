package com.postcard.model;

import java.util.List;

import com.postcard.model.PostcardDetails;
import com.postcard.model.SenderAddress;

import lombok.Data;

@Data
public class Order {
	
	private Integer orderId;
	private SenderAddress senderAddress;
	private String createdDate;
	private String status;
	private Integer noOfRecipients;
	private Integer printQty;
	private List<PostcardDetails> recipients;
	
	

}
