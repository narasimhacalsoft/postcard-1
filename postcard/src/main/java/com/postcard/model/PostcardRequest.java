package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PostcardRequest implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -839790461200813306L;

	private SenderAddress senderAddress;
    
    private RecipientAddressRequest recipientAddress;
    
    private String senderText;
    
    private Branding branding;
    
    
}