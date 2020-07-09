package com.postcard.dao;

import com.postcard.model.User;

public interface UserDao {

	User getUserByUserName(String userName);

}
