package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.edu.dao.UserDao;
import com.edu.entity.User;
import com.edu.util.StringUtils;
@Component 
@Repository("userDao") 
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserList(User user,Integer offset,Integer limit) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from User where 1=1");
		if(user.getId()!=null){
			hql.append(" and id= :userId");
		}
		if(!StringUtils.isEmpty(user.getUsername())){
			hql.append(" and username= :userName");
		}
		if(!StringUtils.isEmpty(user.getPassword())){
			hql.append(" and password= :password");
		}
	
		
		Query query = this.getSession().createQuery(hql.toString());
		
		if(user.getId()!=null){
			query.setInteger("userId", user.getId());
		}
		if(!StringUtils.isEmpty(user.getUsername())){
			query.setString("userName", user.getUsername());
		}
		if(!StringUtils.isEmpty(user.getPassword())){
			query.setString("password", user.getPassword());
		}
		
		
		try{
		    List<User> userList;
			
			if(offset!=null && limit!=null){
				userList = (List<User>) query.setFirstResult(offset)
							                .setMaxResults(limit)
							                .list();
			}else{
				userList = (List<User>) query.list();
			}
			
		    logger.debug("find userList success");
			return userList;
		}catch(Exception e){
			logger.debug("find userList fail:"+e.getMessage());
			return null;
		}	
			
	}

	
}
