<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Yangtzeriver's Blog后台管理--主页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

 <jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
            <!-- 主体内容填充 -->
			<ul class="thumbnails">
			    <li class="span2">
				  <a href="<%=basePath%>admin/tags.jsp" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-tags.png" alt="" class="caption">
					<h4>标签管理</h4> 
				  </a>
				</li>
				<li class="span2">
				  <a href="<%=basePath%>admin/documentAdd.jsp" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-article-add.png" alt="" class="caption">
					<h4>文章添加</h4> 
				  </a>
				</li>
				<li class="span2">
				  <a href="<%=basePath%>admin/documentList.jsp" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-article.png" alt="" class="caption"/>
					<h4>文章列表</h4> 
				  </a>
				</li>
				<li class="span2">
				  <a href="<%=basePath%>admin/cacheManage.jsp" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-cache-manage.png" alt="" class="caption"/>
					<h4>缓存管理</h4> 
				  </a>
				</li>
				<li class="span2">
				  <a href="<%=basePath%>admin/ipManage.jsp" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-ip-manage.png" alt="" class="caption"/>
					<h4>IP访问权限管理</h4> 
				  </a>
				</li>
				<li class="span2">
				  <a href="<%=basePath%>admin/systemConfig.jsp" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-system-config.png" alt="" class="caption"/>
					<h4>系统设置</h4> 
				  </a>
				</li>
				<li class="span2">
				  <a href="<%=basePath%>admin/exit.do" class="thumbnail">
				    <img src="<%=basePath%>img/icon-48-exit.png" alt="" class="caption"/>
					<h4>退出</h4> 
				  </a>
				</li>
			</ul>
	   </div>
	</div>
	

    <input type="hidden" id="currentHref" value="home"/>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>