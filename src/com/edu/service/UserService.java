package com.edu.service;

import com.edu.entity.User;

public interface UserService {
	public void insertUser(User user);
	
	public User userLogin(String username,String password);
	
	public User findUser(String username);
}