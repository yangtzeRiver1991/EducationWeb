package com.edu.service;

import com.edu.entity.IPInfo;
import com.edu.entity.PageResult;

public interface IPInfoService {

    public IPInfo insertIPInfo(IPInfo iPInfo);
    
    public IPInfo findIPInfo(String ip);
	
	public PageResult<IPInfo> findIPInfoList(Integer status,Integer offset, Integer limit);
	
	public Integer updateIPInfo(String ip,Integer status,String descrition);
	
	public Integer updateIPInfo(String ip,String languageConfigParameter);
	 
}
