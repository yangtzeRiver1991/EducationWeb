package com.edu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.util.StringUtils;

/**
 * 过滤器：身份登录验证
 * @author YangZe
 *
 */
public class AdminFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest hsRequest = (HttpServletRequest)request;
		HttpServletResponse hsResponse = (HttpServletResponse)response;
		hsResponse.setCharacterEncoding("utf-8");
		hsResponse.setContentType("text/html;charset=UTF-8");
		
		String path = hsRequest.getContextPath();
		String basePath = hsRequest.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		String url = hsRequest.getRequestURI();

		
		if(url.contains("/admin/") && !url.contains("/admin/login")){
			String accessAdmit = (String) hsRequest.getSession().getAttribute("accessAdmit");
			if(StringUtils.switchEmptyStr(accessAdmit).equals("yes")){
				filterChain.doFilter(request, response);
			}
			else{
				hsResponse.sendRedirect(basePath+"admin/login.jsp");
			}
			
		}
		else{
			filterChain.doFilter(request, response);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
