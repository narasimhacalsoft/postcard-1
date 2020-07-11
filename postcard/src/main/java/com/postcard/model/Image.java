package com.postcard.model;

import java.io.Serializable;
import java.sql.Blob;

import lombok.Data;

@Data
public class Image implements Serializable{    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3494493422267165665L;
	private Integer imageId;
    private byte[] image;
    private String imageType;
}
