package com.edu.dao;

import java.util.List;

import com.edu.entity.CacheManage;

public interface CacheManageDao {

    public CacheManage insertCacheManage(CacheManage cacheManage);
    
	public List<CacheManage> findCacheManageList();
	
	public Integer updateCacheManage(CacheManage cacheManage);
}
