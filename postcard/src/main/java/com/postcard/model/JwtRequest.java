package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtRequest implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4630341765572089411L;

	private String username;
	private String password;

}
