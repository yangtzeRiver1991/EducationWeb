<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<meta http-equiv="keywords" content="Yangtzeriver,Blog,Yangtzeriver's Blog,About Me">
    <meta http-equiv="description" content="Yangtzeriver's Blog" />
    <title>Yangtzeriver's blog</title>
    
    <jsp:include page="import_jsCss.jsp" />  
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/index.css">
  </head>
  <body>
	   <div class="mycontainer" id="mainContent" style="display: block;">
	       <jsp:include page="topMenu.jsp"/>
	       
	       <div class="containerb row-fluid show">
	          <div class="margin-top30">
	             <jsp:include page="common_part_aboutme.jsp"/> 
	          </div>  
		   </div>   
		   
		   <jsp:include page="footer.jsp"/>
	   </div>
  </body>
</html>