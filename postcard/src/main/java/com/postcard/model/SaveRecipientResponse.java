package com.postcard.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveRecipientResponse {
	
	private Integer orderId;
	private List<Postcard> postcards;
	
	

}
