<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="<%=basePath%>css/admin/index.css"/>
<script type="text/javascript" src="<%=basePath%>js/admin/topMenu.js"></script>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
		    <div class="topicLogo lt980 cursor-pointer animated shake">Yangtzeriver's Blog</div>
		    
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse"> 
			    <span class="icon-bar"></span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</a>
			<div class="nav-collapse collapse navbar-inverse-collapse">
				<ul class="nav">
					<li>
					   <div class="topicLogo gt980 cursor-pointer animated shake">Yangtzeriver's Blog</div>
					</li>
				</ul>
				<ul class="nav pull-right top-menu">
				    <li id="home" class="active"><a href="<%=basePath%>admin/main.jsp">首页</a></li>
				    <li id="tags"><a href="<%=basePath%>admin/tags.jsp">标签管理</a></li>
				    <li id="documentAdd"><a href="<%=basePath%>admin/documentAdd.jsp">文章添加</a></li>
				    <li id="documentList"><a href="<%=basePath%>admin/documentList.jsp">文章列表</a></li>
				    <li id="chcheManage"><a href="<%=basePath%>admin/cacheManage.jsp">缓存管理</a></li>
				    <li id="ipManage"><a href="<%=basePath%>admin/ipManage.jsp">IP访问权限管理</a></li>
				    <li id="systemConfig"><a href="<%=basePath%>admin/systemConfig.jsp">系统设置</a></li>
				    <li id="exit"><a href="<%=basePath%>admin/exit.do">退出</a></li>
				</ul>
			</div>
		</div>

	</div>
</div>
<input type="hidden" id="background-switch" value="no"/>
<input type="hidden" id="is-admin" value="yes"/>
