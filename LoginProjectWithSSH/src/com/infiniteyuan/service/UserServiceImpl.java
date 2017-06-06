package com.infiniteyuan.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.infiniteyuan.beans.User;
import com.infiniteyuan.dao.UserDao;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String addUser(User user) {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append('{');
		if (userDao.addUser(user).equals("true")) {
			builder.append("id:").append(userDao.getUserID(user)).append(',');
			builder.append("username:\"").append(user.getUserName()).append("\",");
			builder.append("password:\"").append(user.getPassword()).append("\",");
			builder.append("message:\"").append("success").append("\"");
		} else {
			//已经存在
			builder.append("message:\"").append("failed").append("\"");
		}
		builder.append('}');
		builder.append(']');
		return builder.toString();
	}

	@Override
	public String deleteUser(User user) {
		StringBuilder builder = new StringBuilder();
		int userid = -1;
		userid = userDao.getUserID(user);
		builder.append('[');
		builder.append('{');
		if (userDao.deleteUser(userid) != null) {
			builder.append("id:").append(userid).append(',');
			builder.append("message:\"").append("success").append("\"");
		} else {
			builder.append("message:\"").append("failed").append("\"");
		}
		builder.append('}');
		builder.append(']');
		return builder.toString();
	}

	@Override
	public String updateUser(User user) {
		StringBuilder builder = new StringBuilder();
		user.setId(userDao.getUserID(user));
		builder.append('[');
		builder.append('{');
		if (userDao.updateUser(user) != null) {
			builder.append("id:").append(user.getId()).append(',');
			builder.append("username:\"").append(user.getUserName()).append("\",");
			builder.append("password:\"").append(user.getPassword()).append("\",");
			builder.append("message:\"").append("success").append("\"");
		} else {
			builder.append("message:\"").append("failed").append("\"");
		}
		builder.append('}');
		builder.append(']');
		return builder.toString();
	}

	@Override
	public User getUserByName(String username) {
		User user = userDao.getUserByName(username);
		return user;
//		StringBuilder builder = new StringBuilder();
//		builder.append('[');
//		builder.append('{');
//		if (user != null) {
//			builder.append("id:").append(user.getId()).append(',');
//			builder.append("username:\"").append(user.getUserName()).append("\",");
//			builder.append("password:\"").append(user.getPassword()).append("\",");
//			builder.append("message:\"").append("success").append("\"");
//			return user;
//		} else {
//			builder.append("message:\"").append("failed").append("\"");
//			return null;
//		}
//		builder.append('}');
//		builder.append(']');
//		return null;
	}

}
