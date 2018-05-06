package com.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_tag")
public class Tag implements Serializable{
	private static final long serialVersionUID = 4997729670575717817L;

	private Integer id;
	
	private String code;
	
	private Integer level;
	
	private String oneLevelCode;
	
	private String createTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "code",nullable = false,unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "level",nullable = false)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "onelevelcode",nullable = true)
	public String getOneLevelCode() {
		return oneLevelCode;
	}

	public void setOneLevelCode(String oneLevelCode) {
		this.oneLevelCode = oneLevelCode;
	}

	@Column(name = "createtime",nullable = false)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
