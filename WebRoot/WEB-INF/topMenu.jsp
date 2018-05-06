<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>js/topMenu.js"></script>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
		    <div class="topicLogo lt980 cursor-pointer animated shake">${languageConfigParameter.blog_common_topicTitle}</div>
		    
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse"> 
			    <span class="icon-bar"></span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</a>
			<div class="nav-collapse collapse navbar-inverse-collapse">
				<ul class="nav">
					<li>
					   <div class="topicLogo gt980 cursor-pointer animated shake"><img src="<%=basePath%>img/logo.png"/></div>
					</li>
				</ul>
				<ul class="nav pull-right top-menu">
				    <li id="index" class="active"><a href="<%=basePath %>">首页</a></li>
				    <li id="aboutus"><a href="javascript:void(0)">关于我们</a></li>
				    <li id="courseCenter"><a href="javascript:void(0)">选课中心</a></li>
				    <li id="myCourse"><a href="<%=basePath %>user/myCourse.do">我的课程</a></li>
				    <li id="news"><a href="javascript:void(0)">新闻中心</a></li>
				</ul>
			</div>
			
			<div class="userNav">
				<c:choose>
					<c:when test="${!empty sessionScope.user}">
						${sessionScope.user.username},欢迎访问!&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>user/exit.do">退出</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" id="userAdd">注册</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" id="userLogin">登陆</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</div>
</div>


<div class="modal fade user-add-modal" id="user-add-modal" tabindex="-1" role="dialog" aria-labelledby="user-add-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
  <div class="modal-dialog">
    <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title sys-setup-title">用户注册</h4>
         </div>
         
         <div class="modal-body">
		     <div class="form-inline clear-both">
		        <span class="label label-required float-left margin-top2">用户名:</span>&nbsp;
		        <input name="username" type="text" class="form-control" id="username-add" placeholder="由2~10位的数字、字母以及下划线组成"/>
		     </div>
		     
		     <div class="form-inline clear-both">
		        <span class="label label-required float-left margin-top2">手机号:</span>&nbsp;
		        <input name="mobile" type="text" class="form-control" id="mobile-add"/>
		     </div>
		     
		     <div class="form-inline clear-both">
		        <span class="label label-required float-left margin-top2">密码:</span>&nbsp;
		        <input name="password" type="password" class="form-control" id="password-add" placeholder="由6~16位以内的数字、字母以及下划线组成"/>
		     </div>
		     
		     <div class="form-inline clear-both">
		        <span class="label label-required float-left margin-top2">确认密码:</span>&nbsp;
		        <input name="passwordAgain" type="password" class="form-control" id="passwordAgain-add" placeholder="请再次输入密码"/>
		     </div>
         </div>
         
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="user-add-submit">注册</button>
         </div>
    </div>
  </div>
</div>


<div class="modal fade user-login-modal" id="user-login-modal" tabindex="-1" role="dialog" aria-labelledby="user-login-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
  <div class="modal-dialog">
    <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title sys-setup-title">用户登陆</h4>
         </div>
         
         <div class="modal-body">
		     <div class="form-inline clear-both">
		        <span class="label label-required float-left margin-top2">用户名:</span>&nbsp;
		        <input name="username" type="text" class="form-control" id="username-login" placeholder="由2~10位的数字、字母以及下划线组成"/>
		     </div>
		     
		     <div class="form-inline clear-both">
		        <span class="label label-required float-left margin-top2">密码:</span>&nbsp;
		        <input name="password" type="password" class="form-control" id="password-login" placeholder="由6~16位以内的数字、字母以及下划线组成"/>
		     </div>
         </div>
         
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="user-login-submit">登陆</button>
         </div>
    </div>
  </div>
</div>

<input type="hidden" id="needLogin" value="${param.needLogin}"/>