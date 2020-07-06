package com.postcard.model;

import lombok.Data;

@Data
public class SenderRequest {
	
	private String lastname;
	
	
	private String firstname;
	
	
	private String street;
	
	
	private String houseNr;
		
	private String zip;
	
	private String city;


	public SenderRequest(String lastname, String firstname, String street, String houseNr, String zip, String city) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.street = street;
		this.houseNr = houseNr;
		this.zip = zip;
		this.city = city;
	}

	
}
