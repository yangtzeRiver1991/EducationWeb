package com.edu.service;

import java.util.List;

import com.edu.entity.CourseSign;

public interface CourseSignService {
public void insertCourseSign(CourseSign courseSign);
	
	public int updateCourseProgess(Integer progress,Integer courseId);
	
	public List<CourseSign> findByUserCourse(Integer userId,Integer courseId);
	
	public List<CourseSign> findByUser(Integer userId,Integer offset,Integer limit);
	
	public List<CourseSign> findByCourse(Integer courseId,Integer offset,Integer limit);
}