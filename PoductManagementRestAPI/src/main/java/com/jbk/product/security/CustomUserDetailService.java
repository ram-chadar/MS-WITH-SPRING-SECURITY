
package com.jbk.product.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jbk.product.entity.CustomUserDetail;
import com.jbk.product.entity.User;
import com.jbk.product.exception.UserNotFoundException;
import com.jbk.product.service.UserService;
import com.jbk.product.service.UserServiceImpl;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserService service = new UserServiceImpl();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username ... " + username);
		// loading user from db
		CustomUserDetail user = service.loadUserByUsername(username);
		if (user != null) {
			System.out.println("user value "+user);
			return user;
		} else {
			System.out.println("user not found");
			throw new UserNotFoundException("User not found with username: " + username);
		}

	}

}
