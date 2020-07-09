package com.postcard.model;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3568997960532296558L;

	private final String jwttoken;
	private Collection<?> authorities;

}