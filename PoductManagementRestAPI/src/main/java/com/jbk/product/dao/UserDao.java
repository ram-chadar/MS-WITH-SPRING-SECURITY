package com.jbk.product.dao;

import com.jbk.product.entity.CustomUserDetail;
import com.jbk.product.entity.User;

public interface UserDao {
	public boolean addUser(User user);
	public User loginUser(User user);
	public CustomUserDetail loadUserByUsername(String username);
}
