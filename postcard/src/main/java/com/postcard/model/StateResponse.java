package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class StateResponse {
	
	private String cardKey;
	private State state;
	private List<Warning> warnings;

}
