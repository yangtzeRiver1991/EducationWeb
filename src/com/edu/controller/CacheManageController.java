package com.edu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.CacheManage;
import com.edu.entity.Msg;
import com.edu.service.CacheManageService;
import com.edu.util.DateUtil;
import com.edu.util.StringUtils;


@Controller
public class CacheManageController {
    private Logger logger = Logger.getLogger(CacheManageController.class);
    
    @Resource
    private CacheManageService cacheManageService;
    
    @Resource
	private EhCacheCacheManager ehcacheManager;
    
    @RequestMapping(value = "admin/cacheManageList")  
    @ResponseBody
    public Msg cacheManageList(HttpServletRequest request) {
    	try{
    		List<CacheManage> cacheManageList = cacheManageService.findCacheManageList();
    		
    		return new Msg(0,"加载cache列表成功",cacheManageList);
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("加载cache列表失败.The error is:"+e.getMessage());
    		
    		return new Msg(1,"加载cache列表失败");
    	}
    }
    
    @RequestMapping(value = "admin/addCacheManage")  
    @ResponseBody
    public Msg addCacheManage(@ModelAttribute CacheManage cacheManage,HttpServletRequest request) {
    	try{
    		cacheManage.setCreateDate(DateUtil.getTimeTwo());
    		cacheManage.setUpdateDate(DateUtil.getTimeTwo());
    		
    		cacheManageService.insertCacheManage(cacheManage);
    		
    		return new Msg(0,"addCacheManage success");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("addCacheManage失败.The error is:"+e.getMessage());
    		
    		return new Msg(1,"addCacheManage fail");
    	}
    }
    
    @RequestMapping(value = "admin/updateCacheManage")  
    @ResponseBody
    public Msg updateCacheManage(@ModelAttribute CacheManage cacheManage,HttpServletRequest request) {
    	try{
    		cacheManage.setUpdateDate(DateUtil.getTimeTwo());
    		
    		cacheManageService.updateCacheManage(cacheManage);
    		
    		return new Msg(0,"updateCacheManage success");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("updateCacheManage失败.The error is:"+e.getMessage());
    		
    		return new Msg(1,"updateCacheManage fail");
    	}
    }
    
    
    
    @RequestMapping(value = "admin/refreshCache")  
    @ResponseBody
    public Msg refreshCache(String cahceCode,String cacheKey,HttpServletRequest request) {
    	if(StringUtils.isEmpty(cahceCode)){
    		return new Msg(1,"cache不能为空");
    	}
    	
    	try{
    		EhCacheCache ehCacheCache =  (EhCacheCache)ehcacheManager.getCache(cahceCode);
    		
    		if(ehCacheCache == null){
    			return new Msg(1,"该缓存code不存在");
    		}
    		
    		if(StringUtils.isEmpty(cacheKey)){
    			ehCacheCache.clear();
    			logger.info("cache刷新成功,缓存code="+cahceCode);
    		}else{
    			ValueWrapper valueWrapper = ehCacheCache.get(cacheKey);
    			if(valueWrapper==null){
    				return new Msg(1,"该缓存code对应的key不存在");
    			}
    			
    			ehCacheCache.evict(cacheKey);
    			logger.info("cache刷新成功,缓存code="+cahceCode+",key="+cacheKey);
    		}
    		
    		return new Msg(0,"cache刷新成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("cache刷新失败,缓存code="+cahceCode+",key="+cacheKey+".The error is:"+e.getMessage());
    		return new Msg(1,"cache刷新失败");
    	}
    }
}
