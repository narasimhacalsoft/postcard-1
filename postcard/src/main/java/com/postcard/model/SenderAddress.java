package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SenderAddress implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String lastname;
	
	
	private String firstname;
	
	
	private String street;
	
	
	private String houseNr;
		
	private String zip;
	
	private String city;


	public SenderAddress(String lastname, String firstname, String street, String houseNr, String zip, String city) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.street = street;
		this.houseNr = houseNr;
		this.zip = zip;
		this.city = city;
	}
	public SenderAddress() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
