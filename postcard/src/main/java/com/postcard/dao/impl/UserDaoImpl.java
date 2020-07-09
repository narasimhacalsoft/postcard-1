package com.postcard.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.dao.UserDao;
import com.postcard.model.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	@Value("${getUserQuery}")
	private String getUserQuery;

	@Override
	public User getUserByUserName(String userName) {
		return queryForObject(getUserQuery, User.class, userName);
	}

}
