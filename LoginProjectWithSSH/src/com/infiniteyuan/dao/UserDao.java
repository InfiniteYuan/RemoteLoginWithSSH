package com.infiniteyuan.dao;

import com.infiniteyuan.beans.User;

public interface UserDao {

	String addUser(User user);

	String deleteUser(int userid);

	String updateUser(User user);

	User getUserByName(String username);
	
	int getUserID(User user);
}
