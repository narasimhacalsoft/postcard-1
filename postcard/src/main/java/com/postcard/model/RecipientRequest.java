package com.postcard.model;

import lombok.Data;

@Data
public class RecipientRequest {
	
	private String lastname;
	
	private String firstname;
	
	private String company;	
	
	private String street;
	
	private String houseNr;
	
	private String zip;
	
	private String city; 
	
	private String country;
	
	private String poBox;

	public RecipientRequest(String lastname, String firstname, String company, String street, String houseNr,
			String zip, String city, String country, String poBox) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.company = company;
		this.street = street;
		this.houseNr = houseNr;
		this.zip = zip;
		this.city = city;
		this.country = country;
		this.poBox = poBox;
	}
	
	
	
}
