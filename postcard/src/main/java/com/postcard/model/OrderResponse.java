package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
	
	private List<Order> orders;
}
