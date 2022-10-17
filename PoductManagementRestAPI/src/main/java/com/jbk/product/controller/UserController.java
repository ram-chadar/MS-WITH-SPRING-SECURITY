package com.jbk.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.product.entity.User;
import com.jbk.product.exception.UserAlreadyExistsException;
import com.jbk.product.exception.UserNotFoundException;
import com.jbk.product.service.UserService;

@RestController
//@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping(value = "/adduser")
	public ResponseEntity<Boolean> userlogin(@RequestBody User user) {
		boolean isAdded = service.addUser(user);
		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
		} else {
			throw new UserAlreadyExistsException("User Already Exists With username>> " + user.getUserName());

		}
	}

	@PostMapping(value = "/loginuser")
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		User usr = service.loginUser(user);
		if (usr != null) {
			return new ResponseEntity<User>(usr, HttpStatus.OK);
		} else {
			throw new UserNotFoundException("Login Faild with username >> " + user.getUserName()+" and password >> "+user.getPassword());
		}
	}

}
