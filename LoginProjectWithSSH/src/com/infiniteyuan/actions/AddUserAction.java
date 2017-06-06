package com.infiniteyuan.actions;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.infiniteyuan.beans.User;
import com.infiniteyuan.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AddUserAction extends ActionSupport implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4520887436548395541L;

	private User user = new User();
	@Resource(name = "userService")
	private UserService userService;

	@Override
	public String execute() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if (user != null) {
			printWriter.println(userService.addUser(user));
			printWriter.flush();
			printWriter.close();
		}
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
}
