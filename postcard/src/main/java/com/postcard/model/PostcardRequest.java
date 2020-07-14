package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PostcardRequest implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Integer orderId;

	private SenderAddress senderAddress;
    
    private RecipientAddress recipientAddress;
    
    private String senderText;
    
    private Branding branding;
    
    
}