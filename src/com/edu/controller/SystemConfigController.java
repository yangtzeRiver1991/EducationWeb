package com.edu.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.Msg;
import com.edu.util.StringUtils;


@Controller
public class SystemConfigController {
    private Logger logger = Logger.getLogger(SystemConfigController.class);
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "admin/systemConfig")  
    @ResponseBody
    public Msg systemConfig(String fun,String state,HttpServletRequest request) {
    	try{
    		if(StringUtils.isEmpty(fun) || StringUtils.isEmpty(state)){
        		return new Msg(1,"非法参数");
        	}
        	
        	HashMap<String,String> systemConfig = (HashMap<String, String>) request.getServletContext().getAttribute("systemConfig");
        	
        	if("comment_switch".equals(fun)){
        		if("true".equals(state)){
        			systemConfig.put("comment_switch", "on");
            	}else{
            		systemConfig.put("comment_switch", "off");
            	}
        	}else if("attachment_download_switch".equals(fun)){
        		if("true".equals(state)){
        			systemConfig.put("attachment_download_switch", "on");
            	}else{
            		systemConfig.put("attachment_download_switch", "off");
            	}
        	}else if("system_language_default".equals(fun)){
        		if("true".equals(state)){
        			systemConfig.put("system_language_default", "en");
            	}else{
            		systemConfig.put("system_language_default", "cn");
            	}
        	}else if("comment_content_word_limit".equals(fun)){
        		systemConfig.put("comment_content_word_limit", state);
        	}else if("comment_content_img_limit".equals(fun)){
        		systemConfig.put("comment_content_img_limit", state);
        	}else if("attachment_download_interval_limit".equals(fun)){
        		systemConfig.put("attachment_download_interval_limit", state);
        	}else if("illegal_argument_limit".equals(fun)){
        		systemConfig.put("illegal_argument_limit", state);
        	}
        	
        	request.getServletContext().setAttribute("systemConfig", systemConfig);
        	logger.info("系统参数设置成功");
        	
        	return new Msg(0,"系统参数设置成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.info("系统参数设置失败，原因为"+e.getMessage());
        	
        	return new Msg(1,"系统参数设置失败");
    	}
    }
    
}
