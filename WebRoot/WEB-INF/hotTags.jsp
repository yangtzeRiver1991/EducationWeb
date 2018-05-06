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
	<meta http-equiv="keywords" content="Yangtzeriver,Blog,Yangtzeriver's Blog,Hot Tags">
    <meta http-equiv="description" content="Yangtzeriver's Blog" />
    <title>Yangtzeriver's blog</title>
    
    <jsp:include page="import_jsCss.jsp" />  
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/index.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/hotTags.css">
  </head>
  <body>
	   <div class="mycontainer" id="mainContent" style="display: block;">
	       <jsp:include page="topMenu.jsp"/>
	       
	       <div class="containerb row-fluid show">
	           <div class="panel clound-tag">
			         <div class="panel-heading">
			           <h3 class="panel-title">${languageConfigParameter.blog_index_topmenu_menu_ten}</h3>
			         </div>
			        
			         <div class="panel-body"> 
			             <table class="clound-tag-table">
			                 <tr>
			                    <td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="server" data-tag="tomcat">tomcat</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="server" data-tag="jboss">jboss</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="server" data-tag="nginx">nginx</a></td>
			                 </tr>
			                 
			                 <tr>
			                    <td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="bootstrap">bootstrap</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="jqueryEasyui">jquery easyui</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="java" data-tag="ehcache">ehcache</a></td>
			                 </tr>
			                 
			                 <tr>
			                    <td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="html5">html5</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="css3">css3</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="blockUi">blockUI</a></td>
			                 </tr>
			                 
			                  <tr>
			                    <td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="system" data-tag="ubuntu">ubuntu</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="tools" data-tag="eclipse">eclipse</a></td>
								<td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="tools" data-tag="sublime">sublime</a></td>
			                 </tr>
			                 
			                 <tr>
			                    <td><a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="tools" data-tag="notepad">notepad++</a></td>
			                 </tr>
			             </table>	
			         </div>
                </div>
		   </div>   
		   
		   <jsp:include page="footer.jsp"/>
	   </div>
  </body>
</html>