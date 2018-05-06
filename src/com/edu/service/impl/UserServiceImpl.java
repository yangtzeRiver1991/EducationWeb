package com.edu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Repository;

import com.edu.dao.UserDao;
import com.edu.entity.User;
import com.edu.service.UserService;
@Repository("userService") 
public class UserServiceImpl implements UserService {
	@Resource  
	private UserDao  userDao;
	
	@Resource
	private EhCacheCacheManager ehcacheManager;
	
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		userDao.insertUser(user);
	}


	@Override
	public User userLogin(String username, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		List<User> userList = userDao.findUserList(user, null, null) ;
		
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public User findUser(String username) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(username);
		
        List<User> userList = userDao.findUserList(user, null, null);
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

}