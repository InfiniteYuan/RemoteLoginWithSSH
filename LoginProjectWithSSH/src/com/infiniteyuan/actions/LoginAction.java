package com.infiniteyuan.actions;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import com.infiniteyuan.beans.*;
import com.infiniteyuan.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2738000658350839629L;
	private User user = new User();
	@Resource(name = "userService")
	private UserService userService;

	public UserService getUserService() {
		// 使用注入方式
		// ApplicationContext applicationContext = new
		// ClassPathXmlApplicationContext("SpringConfig.xml");
		// userService = (UserService)
		// applicationContext.getBean("userService");
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append('{');
		System.out.println(userService + "   execute loginaction");
		if (user != null) {
			User mUser = getUserService().getUserByName(user.getUserName());
			if ((mUser != null) && mUser.getPassword().equals(user.getPassword())) {
				builder.append("id:").append(user.getId()).append(',');
				builder.append("username:\"").append(user.getUserName()).append("\",");
				builder.append("password:\"").append(user.getPassword()).append("\",");
				builder.append("message:\"").append("success").append("\"");
			} else {
				builder.append("message:\"").append("failed").append("\"");
			}
			builder.append('}');
			builder.append(']');
			printWriter.println(builder.toString());
			printWriter.flush();
			printWriter.close();
		}
		System.out.println(user);
		return SUCCESS;
	}

	public void addUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if (user != null) {
			printWriter.println(userService.addUser(user));
			printWriter.flush();
			printWriter.close();
		}
	}

	public void deleteUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if (user != null) {
			printWriter.println(userService.deleteUser(user));
			printWriter.flush();
			printWriter.close();
		}
	}

	public void updateUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if (user != null) {
			printWriter.println(userService.updateUser(user));
			printWriter.flush();
			printWriter.close();
		}
	}

	public void login() throws IOException {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append('{');
		System.out.println(userService);
		if (user != null) {
			User mUser = getUserService().getUserByName(user.getUserName());
			if ((mUser != null) && mUser.getPassword().equals(user.getPassword())) {
				builder.append("id:").append(mUser.getId()).append(',');
				builder.append("username:\"").append(mUser.getUserName()).append("\",");
				builder.append("password:\"").append(mUser.getPassword()).append("\",");
				builder.append("message:\"").append("success").append("\"");
			} else {
				builder.append("message:\"").append("failed").append("\"");
			}
			builder.append('}');
			builder.append(']');
			printWriter.println(builder.toString());
			printWriter.flush();
			printWriter.close();
		}
	}

}
