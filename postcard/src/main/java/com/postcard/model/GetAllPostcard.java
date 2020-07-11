package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAllPostcard {
	
	private Integer orderId;
	private List<Postcard> postcards;
	private PostcardOrder postcardOrder;

}
