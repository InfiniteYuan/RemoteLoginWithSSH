package com.infiniteyuan.service;

import com.infiniteyuan.beans.User;

public interface UserService {

	String addUser(User user);

	String deleteUser(User user);

	String updateUser(User user);

	User getUserByName(String username);

}
