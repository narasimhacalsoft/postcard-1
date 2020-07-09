package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 833843870492302272L;
	
	private String userName;
	private String password;
	private String role;

}
