package com.infiniteyuan.utils;

import java.util.Map;

import com.infiniteyuan.actions.LoginAction;
import com.infiniteyuan.beans.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckLoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4115200324712370193L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		System.out.println("begin check login interceptor!");
		// 对LoginAction不做该项拦截
		Object action = actionInvocation.getAction();
		if (action instanceof LoginAction) {
			// System.out.println("exit check login, because this is login
			// action.");
			// UserInfoAction userinfoAction = (UserInfoAction)action;
			return actionInvocation.invoke();
		}
		// 确认Session中是否存在User
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		User user = (User) session.get("user");
		if (user != null) {
			// 存在的情况下进行后续操作。
			// System.out.println("already login!");
			return actionInvocation.invoke();
		} else {
			// 否则终止后续操作，返回LOGIN
			System.out.println("no login, forward login page!");
			return "login";
		}
	}

}
