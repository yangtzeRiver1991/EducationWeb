package com.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_comment")
public class Comment implements Serializable{
	private static final long serialVersionUID = 7098682966494723523L;

	private Integer id;
	
	private Integer docmentId;
	
	private String commentContent;
	
	private String commentDate;
	
	private String commentUser;
	
	private String ip;
	
	private Integer agreeNum;
	
	private Integer againstNum;

	private Integer status;//0正常 1废弃
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "docmentid",nullable = false)
	public Integer getDocmentId() {
		return docmentId;
	}

	public void setDocmentId(Integer docmentId) {
		this.docmentId = docmentId;
	}

	@Column(name = "commentcontent",nullable = false)
	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	@Column(name = "commentdate",nullable = false)
	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	@Column(name = "commentuser",nullable = false)
	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}

	@Column(name = "ip",nullable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "agreeNum",nullable = false)
	public Integer getAgreeNum() {
		return agreeNum;
	}

	public void setAgreeNum(Integer agreeNum) {
		this.agreeNum = agreeNum;
	}

	@Column(name = "againstNum",nullable = false)
	public Integer getAgainstNum() {
		return againstNum;
	}

	public void setAgainstNum(Integer againstNum) {
		this.againstNum = againstNum;
	}

	@Column(name = "status",nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
