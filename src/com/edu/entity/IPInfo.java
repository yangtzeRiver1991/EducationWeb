package com.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_ipinfo")
public class IPInfo implements Serializable{
	private static final long serialVersionUID = -4906998794753118256L;

	private Integer id;
	
	private String ip;
	
	private String ipSite;
	
	private Integer status;//0正常  1黑名单
	
	private String languageConfigParameter;
	
	private String descrition;
	
	private String createDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ip",unique=true,nullable=false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "ipSite",nullable=false)
	public String getIpSite() {
		return ipSite;
	}

	public void setIpSite(String ipSite) {
		this.ipSite = ipSite;
	}

	@Column(name = "status",nullable=false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "languageConfigParameter",nullable=true,length=2)
	public String getLanguageConfigParameter() {
		return languageConfigParameter;
	}

	public void setLanguageConfigParameter(String languageConfigParameter) {
		this.languageConfigParameter = languageConfigParameter;
	}

	@Column(name = "descrition",nullable=false,length=800)
	public String getDescrition() {
		return descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	@Column(name = "createDate",nullable=false)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	
}
