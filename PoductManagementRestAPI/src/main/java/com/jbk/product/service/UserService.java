package com.jbk.product.service;

import com.jbk.product.entity.CustomUserDetail;
import com.jbk.product.entity.User;

public interface UserService {
	
	public boolean addUser(User user);
	public User loginUser(User user);
	public CustomUserDetail loadUserByUsername(String username);

}
