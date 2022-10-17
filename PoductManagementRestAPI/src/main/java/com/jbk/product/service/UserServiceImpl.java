package com.jbk.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jbk.product.dao.UserDao;
import com.jbk.product.entity.CustomUserDetail;
import com.jbk.product.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public PasswordEncoder encoder;
	
	@Autowired
	private UserDao dao;

	@Override
	public boolean addUser(User user) {
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return dao.addUser(user);
	}

	@Override
	public User loginUser(User user) {
		
		String encodedPassword = encoder.encode(user.getPassword());
		System.out.println(encodedPassword);
		user.setPassword(encodedPassword);
		return dao.loginUser(user);
	}

	@Override
	public CustomUserDetail loadUserByUsername(String username) {
		System.out.println("service..."+username);
		return dao.loadUserByUsername(username);
	}

}
