package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class State {
	
	private String state;
	private List<Integer> date;
	private Warning warning;

}
