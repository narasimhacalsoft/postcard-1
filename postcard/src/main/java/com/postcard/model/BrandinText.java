package com.postcard.model;

import lombok.Data;

@Data
public class BrandinText {
	
	public BrandinText(String text, String blockColor, String textColor) {
		super();
		this.text = text;
		this.blockColor = blockColor;
		this.textColor = textColor;
	}
	private String text;
	private String blockColor;
	private String textColor;

}
