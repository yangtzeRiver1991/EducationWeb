package com.edu.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.edu.entity.IPInfo;
import com.edu.init.LanguageConfigParameterInit;
import com.edu.init.SystemConfigInit;
import com.edu.service.IPInfoService;
import com.edu.util.AccessUtils;
import com.edu.util.DateUtil;
import com.edu.util.StringUtils;

/**
 * 过滤器：通用验证类
 * @author yangze
 * @createdate 2016-06-06
 * @version 1.1
 */
public class CommonFilter implements Filter{
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		ServletContext application = request.getServletContext();
		HttpServletRequest hsRequest = (HttpServletRequest)request;
		HttpSession session = hsRequest.getSession();
		String ip = AccessUtils.getIpAddr(hsRequest);;
		Object sesObj_langCon = session.getAttribute("languageConfigParameter");
		
		//读取系统变量
		Object appObj_sysCon= application.getAttribute("systemConfig");
		if(appObj_sysCon == null){
			application.setAttribute("systemConfig", SystemConfigInit.getSystemConfig());
		} 
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		IPInfoService iPInfoService = (IPInfoService) ctx.getBean("iPInfoService");
		IPInfo ipInfo = iPInfoService.findIPInfo(ip);
		
		//读取系统语言设置以及个性化设置
		if(sesObj_langCon == null){
            if(ipInfo != null && !StringUtils.isEmpty(ipInfo.getLanguageConfigParameter())){
            	session.setAttribute("languageConfigParameter", LanguageConfigParameterInit.getLanguageConfigParameter(ipInfo.getLanguageConfigParameter()));
            }else{
            	HashMap<String,String> systemConfig = (HashMap<String, String>) application.getAttribute("systemConfig");
            	String defaultSysLang = systemConfig.get("system_language_default");
            	
            	session.setAttribute("languageConfigParameter",LanguageConfigParameterInit.getLanguageConfigParameter(defaultSysLang));
            }
			
		}
		
		
		
		//ip权限访问控制
		if(ipInfo!=null && ipInfo.getStatus()==1){
			String url = hsRequest.getRequestURI();
			if(url.contains("ipAccessLimit.do") || url.contains("error") || url.contains("ipAccessTooMore.do") || url.contains("illegalArgument.do")){
				filterChain.doFilter(request, response);
			}else{
				String path = hsRequest.getContextPath();
				String basePath = hsRequest.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				HttpServletResponse hsResponse = (HttpServletResponse)response;
				
				hsResponse.sendRedirect(basePath+"ipAccessLimit.do");
			}
		}else{
			if(ipInfo == null){
				IPInfo ipInfo_new = new IPInfo();
				ipInfo_new.setIp(ip);
				ipInfo_new.setIpSite(AccessUtils.getAddressesMore(ip, "utf-8"));
				ipInfo_new.setCreateDate(DateUtil.getTimeTwo());
				ipInfo_new.setStatus(0);
				ipInfo_new.setDescrition("<p>系统于"+DateUtil.getTimeTwo()+"添加ip访问信息</p>");
				
				iPInfoService.insertIPInfo(ipInfo_new);
			}
			
			HashMap<String,String> systemConfig = (HashMap<String, String>) application.getAttribute("systemConfig");
			
			//非法参数访问控制
			Integer illegalArgumentCount = (Integer) hsRequest.getSession().getAttribute("illegalArgumentCount");
			int  illegalArgumentLimit = Integer.parseInt(systemConfig.get("illegal_argument_limit"));
            if(illegalArgumentCount!=null && illegalArgumentCount>illegalArgumentLimit){
            	iPInfoService.updateIPInfo(ip, 1, ipInfo.getDescrition()+"<p>系统于"+DateUtil.getTimeTwo()+"将此ip拉入黑名单.原因为:illegalArgumentCount超过阀值</p>");
            	
            	String path = hsRequest.getContextPath();
				String basePath = hsRequest.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				HttpServletResponse hsResponse = (HttpServletResponse)response;
				
            	hsResponse.sendRedirect(basePath+"ipAccessLimit.do");
            }else{
            	//DDOS攻击控制
            	Integer ipAccessTooMoreCount = (Integer) hsRequest.getSession().getAttribute("ipAccessTooMoreCount");
    			int  ipAccessTooMoreLimit = Integer.parseInt(systemConfig.get("access_more_limit"));
    			if(ipAccessTooMoreCount!=null && ipAccessTooMoreCount>ipAccessTooMoreLimit){
                	iPInfoService.updateIPInfo(ip, 1, ipInfo.getDescrition()+"<p>系统于"+DateUtil.getTimeTwo()+"将此ip拉入黑名单.原因为:ipAccessTooMoreCount超过阀值</p>");
                	
                	String path = hsRequest.getContextPath();
    				String basePath = hsRequest.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    				HttpServletResponse hsResponse = (HttpServletResponse)response;
    				
                	hsResponse.sendRedirect(basePath+"ipAccessLimit.do");
                }else{
                	filterChain.doFilter(request, response);
                }
            }
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
