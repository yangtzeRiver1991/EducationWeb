<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<meta name="renderer" content="webkit">
<link href="<%=basePath%>css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/animate.css/3.1.0/animate.min.css">
<link href="<%=basePath%>css/base.css" rel="stylesheet">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.8.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base.js"></script>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script type="text/javascript" src="http://cdn.bootcss.com/html5shiv/3.7/html5shiv.min.js"></script>
      <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->