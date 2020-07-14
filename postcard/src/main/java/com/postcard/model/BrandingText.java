package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class BrandingText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BrandingText(String text, String blockColor, String textColor) {
		super();
		this.text = text;
		this.blockColor = blockColor;
		this.textColor = textColor;
	}
	private String text;
	private String blockColor;
	private String textColor;
	
	public BrandingText() {
		super();
		// TODO Auto-generated constructor stub
	}

}
