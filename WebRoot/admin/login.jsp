<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>登录</title>

    <jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
  </head>
  <body>
	   <form action="<%=basePath%>admin/login.do" style="margin:0 auto;width:300px;margin-top: 150px;">
	         <input name="accessCode" type="password" class="input-block-level" placeholder="请输入访问码"/>
	         <input type="submit" value="提交"/>
	   </form>
  </body>
  <input type="hidden" id="path" value="<%=basePath%>"/>
  <input type="hidden" id="background-switch" value="no"/>
</html>