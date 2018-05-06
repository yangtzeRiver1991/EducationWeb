package com.edu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.dao.CacheManageDao;
import com.edu.entity.CacheManage;
import com.edu.service.CacheManageService;

@Service("cacheManageService")
public class CacheManageServiceImpl implements CacheManageService{

	@Resource 
	private CacheManageDao cacheManageDao;

	@Override
	public CacheManage insertCacheManage(CacheManage cacheManage) {
		// TODO Auto-generated method stub
		return cacheManageDao.insertCacheManage(cacheManage);
	}

	@Override
	public List<CacheManage> findCacheManageList() {
		// TODO Auto-generated method stub
		return cacheManageDao.findCacheManageList();
	}

	@Override
	public Integer updateCacheManage(CacheManage cacheManage) {
		// TODO Auto-generated method stub
		return cacheManageDao.updateCacheManage(cacheManage);
	}
	
}
