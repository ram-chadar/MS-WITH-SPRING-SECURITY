package com.jbk.product.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.product.entity.CustomUserDetail;
import com.jbk.product.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public boolean addUser(User user) {
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		boolean isAdded = false;
		System.out.println("dao add user>> "+user);
		try {
			User usr = session.get(User.class, user.getUserName());
			if (usr == null) {
				session.save(user);
				transaction.commit();
				isAdded = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isAdded;
	}

	@Override
	public User loginUser(User user) {
		Session session = sf.openSession();
		User usr = null;
		try {
			usr = session.get(User.class, user.getUserName());
			if (usr.getPassword().equals(user.getPassword())) {
				return usr;
			} else {
				usr = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return usr;

	}

	@Override
	public CustomUserDetail loadUserByUsername(String username) {
		Session session = sf.openSession();
		CustomUserDetail user = new CustomUserDetail();
		User usr = null;
		try {
			usr = session.get(User.class, username);
			if(usr!=null) {
			user.setUserid(usr.getUserName());
			user.setPassword(usr.getPassword());
			user.setRoles(usr.getRoles());
			}
			System.out.println("dao ..." + user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return user;

	}

}
