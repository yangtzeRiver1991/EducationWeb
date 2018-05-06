<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
    <title>error-出错了</title>
    <jsp:include page="import_jsCss.jsp" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/error.css">
  </head>
  <body>
	   <div class="error-show">
	      <div class="error-title">
	        <span class="content">
	           <c:choose>
	             <c:when test="${!empty errorCode}">${errorCode}</c:when>
	             <c:otherwise>404</c:otherwise>
	           </c:choose>
	        </span>
	      </div>
	      
	      <div class="error-detail">
	         <span class="detail-main">${languageConfigParameter.blog_common_error_page_title}</span>
	         <ul class="detail-child">
	           <li>${languageConfigParameter.blog_common_error_page_suggest_one}</li>
	           <li>${languageConfigParameter.blog_common_error_page_suggest_two}</li>
	         </ul>
	         
	         <a class="btn btn-block btn-primary backbutton" type="button" href="<%=basePath%>">${languageConfigParameter.blog_common_error_page_return_link}</a>
	      </div>
	   </div>
  </body>
</html>