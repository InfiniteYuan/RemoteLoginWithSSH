package com.infiniteyuan.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.infiniteyuan.beans.User;
import com.infiniteyuan.dao.UserDao;

public class UserDaoImpl implements UserDao {

	private Configuration mConfiguration;
	private SessionFactory mSessionFactory;

	public UserDaoImpl() {
		this.mConfiguration = new Configuration().configure();
		this.mSessionFactory = mConfiguration.buildSessionFactory();
	}

	@Override
	public String addUser(User user) {
		if (getUserByName(user.getUserName()) == null) {
			Session session = mSessionFactory.openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			session.close();
			return "true";
		}else {
			return "false";
		}
	}

	@Override
	public String deleteUser(int userid) {
		User user = new User();
		user.setId(userid);
		Session session = mSessionFactory.openSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
		session.close();
		return "true";
	}

	@Override
	public String updateUser(User user) {
		Session session = mSessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
		return "true";
	}

	@Override
	public User getUserByName(String username) {
		System.out.println(username + "    userdao");
		String hql = "from User where userName=:name";
		Session session = mSessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setString("name", username);
		User user = (User) query.uniqueResult();
		if (user != null) {
			System.out.println(user.getId() + "\tididididid ");
		}
		return user;
	}

	@Override
	public int getUserID(User user) {
		String hql = "from User where userName=:name";
		Session session = mSessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setString("name", user.getUserName());
		User muser = (User) query.uniqueResult();
		if(muser!=null)
			return muser.getId();
		else 
			return -1;
	}

}
