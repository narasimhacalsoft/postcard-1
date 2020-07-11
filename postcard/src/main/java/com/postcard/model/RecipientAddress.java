package com.postcard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RecipientAddress implements Serializable{
	
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
	private List<String> errors = new ArrayList<>();

}
