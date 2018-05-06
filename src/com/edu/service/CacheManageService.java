package com.edu.service;

import java.util.List;

import com.edu.entity.CacheManage;

public interface CacheManageService {

    public CacheManage insertCacheManage(CacheManage cacheManage);
    
	public List<CacheManage> findCacheManageList();
	
	public Integer updateCacheManage(CacheManage cacheManage);
	 
}
