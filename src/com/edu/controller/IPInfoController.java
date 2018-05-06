package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.IPInfo;
import com.edu.entity.Msg;
import com.edu.entity.PageResult;
import com.edu.init.ErrorCodeInit;
import com.edu.init.LanguageConfigParameterInit;
import com.edu.service.IPInfoService;
import com.edu.util.AccessUtils;
import com.edu.util.ArrayUtil;
import com.edu.util.DateUtil;
import com.edu.util.RandomUtils;
import com.edu.util.StringUtils;
import com.edu.validate.SysConfigValidate;


@Controller
public class IPInfoController {
    private Logger logger = Logger.getLogger(IPInfoController.class);
    
    @Resource
    private IPInfoService iPInfoService;
   
    @RequestMapping(value = "ipAccessTooMore")  
    public String error(HttpServletRequest request,Model model) {
    	AccessUtils.recordIpAccessTooMore(request);
    	model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("ipAccessTooMore").toString()));
    	
        return "WEB-INF/error";
    }
    
    @RequestMapping(value = "illegalArgument")  
    public String illegalArgument(HttpServletRequest request,Model model) {
    	AccessUtils.recordIllegalArgumentAccess(request);
    	model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("illegalArgument").toString()));
		
        return "WEB-INF/error";
    }
    
    @RequestMapping(value = "ipAccessLimit")  
    public String ipAccessLimit(HttpServletRequest request,Model model) {
        model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("ipAccessLimit").toString()));
        return "WEB-INF/error";
    }
    
    @RequestMapping(value = "admin/iPInfoList")  
    @ResponseBody
    public PageResult<IPInfo> iPInfoList(String ip,Integer status,Integer offset,Integer limit,HttpServletRequest request) {
    	try{
    		PageResult<IPInfo> pageResult;
    		
    		if(StringUtils.isEmpty(ip)){
    			pageResult = iPInfoService.findIPInfoList(status, offset, limit);
    		}else{
    			IPInfo iPInfo = iPInfoService.findIPInfo(ip);
    			
    			if(iPInfo != null){
    				List<IPInfo> iPInfoList = new ArrayList<IPInfo>();
    				iPInfoList.add(iPInfo);
    				
    				pageResult = new PageResult<IPInfo>(iPInfoList,1, 0);
    			}else{
    				pageResult = new PageResult<IPInfo>(null,0, 0);
    			}
    		}
	
    		logger.info("后台加载IP列表成功");
    		return pageResult;
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台加载IP列表失败,原因为:"+e.getMessage());
    		
    		PageResult<IPInfo> pageResult = new PageResult<IPInfo>(null,0,1);
    		
    		return pageResult;
    	}
    }
    
    @RequestMapping(value = "admin/addIPBlack")  
    @ResponseBody
    public Msg addIPBlack(String ip,String descrition,HttpServletRequest request) {
    	try{
    		if(StringUtils.isEmpty(ip)){
        		return new Msg(1,"ip不能为空");
        	}
        	
        	if(StringUtils.isEmpty(descrition)){
        		return new Msg(1,"禁用原因不能为空");
        	}
        	
        	IPInfo existIp = iPInfoService.findIPInfo(ip);
        	if(existIp != null){
        		return new Msg(1,"该ip信息已被记录");
        	}
        	
        	IPInfo ipInfo = new IPInfo();
        	ipInfo.setIp(ip);
        	ipInfo.setIpSite(AccessUtils.getAddressesMore(ip, "utf-8"));
        	ipInfo.setStatus(1);
        	ipInfo.setDescrition("<p>管理员于"+DateUtil.getTimeTwo()+"新增ip黑名单，原因为:"+descrition+"</p>");
        	ipInfo.setCreateDate(DateUtil.getTimeTwo());
        	
    		iPInfoService.insertIPInfo(ipInfo);
    				
    		logger.info("后台新增IP黑名单成功");
    		return new Msg(0,"新增IP黑名单成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台新增IP黑名单失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"新增IP黑名单失败");
    	}
    }
    
    @RequestMapping(value = "admin/updateIPInfo")  
    @ResponseBody
    public Msg updateIPInfo(String ip,Integer operation,String descrition,HttpServletRequest request) {
    	try{
    		if(StringUtils.isEmpty(ip)){
        		return new Msg(1,"ip不能为空");
        	}
        	
        	if(operation == null){
        		return new Msg(1,"操作不能为空");
        	}
        	
        	if(StringUtils.isEmpty(descrition)){
        		return new Msg(1,"备注不能为空");
        	}
        	
        	if(ip.contains(",")){
        		String[] ipArray = ip.split(",");
        		ipArray = ArrayUtil.distinctArray(ipArray);
        		
        		for(String tempIp:ipArray){
        			IPInfo existIp = iPInfoService.findIPInfo(tempIp);
                	if(existIp == null){
                		return new Msg(1,"该ip不存在");
                	}

                	String operationFormat = "";
                	if(operation == 0){
                		operationFormat = "解禁操作";
                	}else{
                		operationFormat = "禁用操作";
                	}
                	descrition = existIp.getDescrition()+"<p>管理员于"+DateUtil.getTimeTwo()+"进行"+operationFormat+".原因为:"+descrition+"</p>";

        			iPInfoService.updateIPInfo(tempIp, operation, descrition);
        		}
        	}else{
        		IPInfo existIp = iPInfoService.findIPInfo(ip);
            	if(existIp == null){
            		return new Msg(1,"该ip不存在");
            	}

            	String operationFormat = "";
            	if(operation == 0){
            		operationFormat = "解禁操作";
            	}else{
            		operationFormat = "禁用操作";
            	}
            	descrition = existIp.getDescrition()+"<p>管理员于"+DateUtil.getTimeTwo()+"进行"+operationFormat+".原因为:"+descrition+"</p>";
        		
        		iPInfoService.updateIPInfo(ip, operation, descrition);
        	}
    				
    		logger.info("更新ip信息状态成功");
    		return new Msg(0,"后台更新ip信息状态成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台更新ip信息状态失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"更新ip信息状态失败");
    	}
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "switchLanguage")  
    @ResponseBody
    public Msg switchLanguage(HttpServletRequest request,String languageConfig,String verificationCode) {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	
    	try{
	    	Msg msg = SysConfigValidate.languageConfigValidate(languageConfig, verificationCode,languageConfigParameter);
	    	if(msg != null){
	    	    return msg;
	    	}
	    	
	    	if(!verificationCode.equals(request.getSession().getAttribute("validateCode"))){
	            Msg nowMsg = new Msg(1,languageConfigParameter.get("blog_index_validate_code_input_error_verification_code_prompt"));
	            return nowMsg;
	        }
	        
	        request.getSession().setAttribute("validateCode", RandomUtils.randomCompose(4, 50));
	        
	        HashMap<String,String> languageConfigParameter_new = LanguageConfigParameterInit.getLanguageConfigParameter(languageConfig);
	        request.getSession().setAttribute("languageConfigParameter", languageConfigParameter_new);
	        
	        iPInfoService.updateIPInfo(AccessUtils.getIpAddr(request), languageConfig);
	        
	        logger.info("系统语言切换成功");
	        
	        return new Msg(0,languageConfigParameter.get("blog_common_operation_success_prompt"));
    	}catch(Exception e){
            e.printStackTrace();
            
            logger.error("系统语言切换失败,原因为:"+e.getMessage());
    		return new Msg(1,languageConfigParameter.get("blog_common_operation_fail_prompt"));
    	}
    }
   
}
