package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.edu.dao.CourseSignDao;
import com.edu.entity.CourseSign;
@Component 
@Repository("courseSignDao") 
public class CourseSignDaoImpl extends HibernateDaoSupport implements CourseSignDao {
	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public void insertCourseSign(CourseSign courseSign) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(courseSign);
	}
	
	public int updateCourseProgess(Integer progress,Integer courseId){
		try{
			String hql = "update CourseSign set progress= :progress where courseId= :courseId";
			
			Query query = this.getSession().createQuery(hql.toString());
			query.setInteger("progress", progress);
			query.setInteger("courseId", courseId);
			
			int i = query.executeUpdate();
			
			logger.info("updateCourseProgess success");
			return i;
		}catch(Exception e){
			logger.error("updateCourseProgess fail");
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseSign> findByUserCourse(Integer userId,Integer courseId){
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from CourseSign where userId= :userId and courseId= :courseId");
		hql.append(" and userId= :userId");
	
		
		Query query = this.getSession().createQuery(hql.toString());
		query.setInteger("userId", userId);
		query.setInteger("courseId", courseId);
		
		
		try{
		    List<CourseSign> courseSignList = (List<CourseSign>) query.list();

		    logger.debug("findByUserCourse courseSignList success");
			return courseSignList;
		}catch(Exception e){
			logger.error("findByUserCourse courseSignList fail:"+e.getMessage());
			return null;
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseSign> findByUser(Integer userId,Integer offset,Integer limit) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from CourseSign where 1=1");
		hql.append(" and userId= :userId");
	
		
		Query query = this.getSession().createQuery(hql.toString());
		
		query.setInteger("userId", userId);
		
		
		try{
		    List<CourseSign> courseSignList;
			
			if(offset!=null && limit!=null){
				courseSignList = (List<CourseSign>) query.setFirstResult(offset)
							                .setMaxResults(limit)
							                .list();
			}else{
				courseSignList = (List<CourseSign>) query.list();
			}
			
		    logger.debug("findByUser courseSignList success");
			return courseSignList;
		}catch(Exception e){
			logger.error("findByUser courseSignList fail:"+e.getMessage());
			return null;
		}	
			
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseSign> findByCourse(Integer courseId,Integer offset,Integer limit) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from CourseSign where 1=1");
		hql.append(" and courseId= :courseId");
	
		
		Query query = this.getSession().createQuery(hql.toString());
		
		query.setInteger("courseId", courseId);
		
		
		try{
		    List<CourseSign> courseSignList;
			
			if(offset!=null && limit!=null){
				courseSignList = (List<CourseSign>) query.setFirstResult(offset)
							                .setMaxResults(limit)
							                .list();
			}else{
				courseSignList = (List<CourseSign>) query.list();
			}
			
		    logger.debug("findByCourse courseSignList success");
			return courseSignList;
		}catch(Exception e){
			logger.error("findByCourse courseSignList fail:"+e.getMessage());
			return null;
		}	
			
	}
	
}
