package com.postcard.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class SwissUtils {
	
	public String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=null;
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		   username = principal.toString();
		}
		return username;
	}

	public String convertCreateDate(Timestamp timestamp) {
		long val = timestamp.getTime();
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM-dd-yyyy");
        String dateText = df2.format(date);
		return dateText;
	}
	
	public String convertCreateDateWithHours(Timestamp timestamp) {
		long val = timestamp.getTime();
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM-dd-yyyy hh:mm:ss a");
        String dateText = df2.format(date);
		return dateText;
	}
}
