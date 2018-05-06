<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="panel friendly-link">
   <div class="panel-heading">
     <h3 class="panel-title">${languageConfigParameter.blog_index_topmenu_menu_eleven}</h3>
   </div>
  
   <div class="panel-body">
        <p>
            <a href="http://resume.yangtzeriver.me" title="一款响应式简历在线生成站" target="_blank">我的简历网</a>
        </p>
        <p>
            <a href="https://cqamin.com" title="AMIN'S BLOG" target="_blank">AMIN'S BLOG</a>
        </p>
   </div>
</div> 