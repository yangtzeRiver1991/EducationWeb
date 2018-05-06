package com.edu.dao;

import java.util.List;

import com.edu.entity.User;

public interface  UserDao{
	public void insertUser(User user);
	
	public List<User> findUserList(User user,Integer offset,Integer limit);
}