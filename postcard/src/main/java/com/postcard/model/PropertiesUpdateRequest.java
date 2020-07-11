package com.postcard.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PropertiesUpdateRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6435111105033196688L;
	private List<NameValue> nameValues;

}
