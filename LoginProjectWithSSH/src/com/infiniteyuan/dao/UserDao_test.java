package com.infiniteyuan.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.infiniteyuan.beans.User;
import com.infiniteyuan.service.UserService;
import com.infiniteyuan.service.UserServiceImpl;

public class UserDao_test {
	
	
	public static void main(String[] args) {
		User user = new User();
		user.setId(1);
		user.setUserName("user");
		user.setPassword("pass");
		
//		Configuration configuration = new Configuration().configure();
//		SessionFactory sessionFactory = configuration.buildSessionFactory();
		
//		Resource resource = new FileSystemResource("SpringConfig.xml");
//		@SuppressWarnings("deprecation")
//		BeanFactory beanFactory = new XmlBeanFactory(resource);
//		UserServiceImpl userServiceImpl = (UserServiceImpl) beanFactory.getBean("userService", UserServiceImpl.class);

		String xmlPath = "SpringConfig.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfig.xml");
		UserService userServiceImpl = (UserService) applicationContext.getBean("userService");
//		UserServiceImpl userServiceImpl =new UserServiceImpl();
		userServiceImpl.getUserByName("user");
//		String hql = "from User where userName=:name";
//		Session session = sessionFactory.openSession();
//		Query query = session.createQuery(hql);
//		String username = "user";
//		query.setString("name", username);
//		user = (User) query.uniqueResult();
		System.out.println(user);
//		session.beginTransaction();
//		session.update(user);
//		session.getTransaction().commit();
//		session.close();
//		sessionFactory.close();
	}
}
