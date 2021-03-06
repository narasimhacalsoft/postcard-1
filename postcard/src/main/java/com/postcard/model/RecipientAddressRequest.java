package com.postcard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class RecipientAddressRequest implements Serializable{
	
    /**
     * 
     */
    private static final long serialVersionUID = 6802108515741862897L;
    private String title;
    private String lastname;	
	private String firstname;	
	private String company;		
	private String street;	
	private String houseNr;	
	private String zip;	
	private String city; 	
	private String country;	
	private String poBox;

	public RecipientAddressRequest(String title, String lastname, String firstname, String company, String street, String houseNr,
			String zip, String city, String country, String poBox) {
		super();
		this.title = title;
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
