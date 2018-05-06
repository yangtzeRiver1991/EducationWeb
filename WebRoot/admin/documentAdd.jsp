<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Yangtzeriver's Blog后台管理--文章添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/wangEditor/wangEditor.min.css">
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
            <!-- 主体内容填充 -->
              <h2 class="text-align-center">文章添加</h2>
	      
		      <div class="form-horizontal">
	             <div class="form-inline">
					<span class="label label-required">文章标题: </span> 
					<input type="text" class="input-block-level blurcheck" id="title" non-empty non-empty-prompt="文章标题不能为空"  data-toggle="tooltip" data-placement="top" data-trigger="manual"/>
				 </div>
				 
				 <div class="form-inline">
					<span class="label label-required" id="tags-show">所属标签: </span> 
					
					
					<div class="clear-both"></div>
				 </div>
				 
				 <div class="form-inline">
					<span class="label label-required">文章简介: </span> 
					<textarea class="input-block-level" id="intro" non-empty non-empty-prompt="文章简介不能为空"   data-toggle="tooltip" data-placement="top" data-trigger="manual"></textarea>
				 </div>
				 
				 <div class="form-inline">
					<span class="label label-required">文章内容: </span> 
				 </div>
				 
				 <div style="width:100%;margin:0 auto;">
				    <div id="editor" style="height:500px;max-height:600px;">
					   <p>努力吧，骚年！</p>
					</div>
				 </div>
		      </div>
	      
		      <div class="btn-div">
		         <button class="btn btn-primary" id="doc-add-button">提交</button>	
		      </div>
	   </div>
	</div>

    <input type="hidden" id="currentHref" value="documentAdd"/>
    <script type="text/javascript" src="<%=basePath%>js/wangEditor/wangEditor.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/admin/editorConfig.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/admin/documentAdd.js"></script>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>