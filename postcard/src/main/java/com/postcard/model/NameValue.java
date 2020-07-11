package com.postcard.model;

import java.io.Serializable;

import lombok.Data;


@Data
public class NameValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1767240771593780779L;
	private String name;
	private String value;

}
