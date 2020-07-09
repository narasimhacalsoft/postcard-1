package com.postcard.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.postcard.dao.UserDao;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.postcard.model.User user = userDao.getUserByUserName(username);
		if (user != null && user.getUserName().equals(username)) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new GrantedAuthority() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public String getAuthority() {
					return user.getRole();
				}
			});
			return new User(username, user.getPassword(), authorities);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}