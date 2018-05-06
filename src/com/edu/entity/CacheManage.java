package com.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_cacheManage")
public class CacheManage implements Serializable{
	private static final long serialVersionUID = -5221020476291394788L;

	private Integer id;
	
	private String code;
	
	private String name;
	
	private Boolean allEntries;
	
	private String descrition;
	
	private String createDate;
	
	private String updateDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="code",unique=true,nullable=false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="name",nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="allEntries",nullable=false)
	public Boolean isAllEntries() {
		return allEntries;
	}

	public void setAllEntries(Boolean allEntries) {
		this.allEntries = allEntries;
	}

	@Column(name="descrition",nullable=false)
	public String getDescrition() {
		return descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	@Column(name="createDate",nullable=false)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name="updateDate",nullable=false)
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
