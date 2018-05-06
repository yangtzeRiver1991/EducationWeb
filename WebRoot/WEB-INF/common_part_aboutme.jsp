<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="panel about-me">
    <div class="panel-heading">
      <h3 class="panel-title">${languageConfigParameter.blog_index_topmenu_menu_twelve}</h3>
    </div>
      
    <div class="panel-body"> 
         <p>${languageConfigParameter.blog_index_aboutme_p1}</p>
         
         <p>${languageConfigParameter.blog_index_aboutme_p2}</p>
         
         <p>${languageConfigParameter.blog_index_aboutme_p3}</p>
    </div>
    
    <div class="panel-heading contact-way">
      <h3 class="panel-title">${languageConfigParameter.blog_index_aboutme_contactway}</h3>
    </div>
    
    <ul class="contact-way-ul"> 
      <li data-concatway="qq">
         <c:choose>
             <c:when test="${!empty mobileQQ_link}">
                <a href="mqqwpa://im/chat?chat_type=wpa&uin=2235822480&version=1" target="_blank">
		           <img src="<%=basePath%>img/qq.png"/>
		        </a>
             </c:when>
             <c:otherwise>
                <a href="tencent://message/?uin=2235822480&Site=在线QQ&Menu=yes" target="_blank">
		           <img src="<%=basePath%>img/qq.png"/>
		        </a>
             </c:otherwise>
         </c:choose>
      </li> 
      <li data-concatway="weixin"><a href="javascript:void(0)" id="me-weixin-link"><img src="<%=basePath%>img/weixin.png"/></a></li>
    </ul>
</div> 