package com.edu.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_course_sign")
public class CourseSign implements Serializable{
	private static final long serialVersionUID = 5153565912828468817L;
	
	private Integer id;
	
    private Integer courseId;
    
    private String courseName;
	
	private Integer userId;
	
	private Integer progress;
	
    private String  createDate;
	
	private String createrIp;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "courseId",nullable = false)
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	@Column(name = "courseName",nullable = false)
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Column(name = "userId",nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "progress",nullable = false)
	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	@Column(name = "createDate",nullable = false)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "createrIp",nullable = false)
	public String getCreaterIp() {
		return createrIp;
	}
	public void setCreaterIp(String createrIp) {
		this.createrIp = createrIp;
	}

}
