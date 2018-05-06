package com.edu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.edu.dao.CourseSignDao;
import com.edu.entity.CourseSign;
import com.edu.service.CourseSignService;
@Repository("courseSignService") 
public class CourseSignServiceImpl implements CourseSignService {
	@Resource  
	private CourseSignDao  courseSignDao;

	@Override
	public void insertCourseSign(CourseSign courseSign) {
		// TODO Auto-generated method stub
		 courseSignDao.insertCourseSign(courseSign);
	}
	
	@Override
	public List<CourseSign> findByUserCourse(Integer userId,Integer courseId){
		// TODO Auto-generated method stub
				return courseSignDao.findByUserCourse(userId, courseId);
	}

	@Override
	public int updateCourseProgess(Integer progress, Integer courseId) {
		// TODO Auto-generated method stub
		return courseSignDao.updateCourseProgess(progress, courseId);
	}

	@Override
	public List<CourseSign> findByUser(Integer userId, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return courseSignDao.findByUser(userId, offset, limit);
	}

	@Override
	public List<CourseSign> findByCourse(Integer courseId, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return courseSignDao.findByCourse(courseId, offset, limit);
	}


	

}