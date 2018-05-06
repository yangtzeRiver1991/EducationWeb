package com.edu.dao;

import java.util.List;

import com.edu.entity.IPInfo;

public interface IPInfoDao {

    public IPInfo insertIPInfo(IPInfo iPInfo);
    
    public IPInfo findIPInfo(String ip);
	
	public List<IPInfo> findIPInfoList(Integer status,Integer offset, Integer limit);
	
	public Integer updateIPInfo(String ip,Integer status,String descrition);
	
	public Integer updateIPInfo(String ip,String languageConfigParameter);
	
	public Integer countIPInfoNum(Integer status);
}
