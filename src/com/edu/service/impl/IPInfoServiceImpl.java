package com.edu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.edu.dao.IPInfoDao;
import com.edu.entity.IPInfo;
import com.edu.entity.PageResult;
import com.edu.service.IPInfoService;

@Service("iPInfoService")
public class IPInfoServiceImpl implements IPInfoService{
	@Resource 
	private IPInfoDao iPInfoDao;

	@Override
	@CacheEvict(value="findIPInfo",key="#iPInfo.ip")
	public IPInfo insertIPInfo(IPInfo iPInfo) {
		// TODO Auto-generated method stub
		return iPInfoDao.insertIPInfo(iPInfo);
	}

	@Override
	@Cacheable(value="findIPInfo",key="#ip")
	public IPInfo findIPInfo(String ip) {
		// TODO Auto-generated method stub
		return iPInfoDao.findIPInfo(ip);
	}

	@Override
	public PageResult<IPInfo> findIPInfoList(Integer status, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		List<IPInfo> iPInfoList = iPInfoDao.findIPInfoList(status,offset, limit);
		Integer iPInfoNum = iPInfoDao.countIPInfoNum(status);
		
		PageResult<IPInfo> pageResult = new PageResult<IPInfo>(iPInfoList, iPInfoNum, 0);
		
		return pageResult;
	}

	@Override
	@CacheEvict(value="findIPInfo",key="#ip")
	public Integer updateIPInfo(String ip, Integer status, String descrition) {
		// TODO Auto-generated method stub
		return iPInfoDao.updateIPInfo(ip, status, descrition);
	}

	@Override
	@CacheEvict(value="findIPInfo",key="#ip")
	public Integer updateIPInfo(String ip, String languageConfigParameter) {
		// TODO Auto-generated method stub
		return iPInfoDao.updateIPInfo(ip, languageConfigParameter);
	}
	
}
