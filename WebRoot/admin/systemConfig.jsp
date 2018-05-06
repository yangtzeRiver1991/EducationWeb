<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Yangtzeriver's Blog后台管理--系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
<link href="//cdn.bootcss.com/bootstrap-switch/3.3.2/css/bootstrap2/bootstrap-switch.min.css" rel="stylesheet">
<script src="//cdn.bootcss.com/bootstrap-switch/3.3.2/js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/systemConfig.js"></script>
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
	       <h2 class="text-align-center">系统设置</h2>
           
           <div class="form-inline">
              <ul class="tag-level1">
                 <li>
                     <span class="label label-required">评论设置: </span>
                     <ul class="tag-level2_2">
                        <li>
                           <span class="label label-required">开关: </span> 
                           <c:choose>
                               <c:when test="${systemConfig.comment_switch=='on'}">
                                   <input type="checkbox" class="bootstrap-switch" onText="开" offText="关" handleWidth="50px" labelWidth="50px" switch-size="normal" onColor="success" offColor="warning" fun="comment_switch" switch="true">
                               </c:when>
                             
                               <c:otherwise>
                                   <input type="checkbox" class="bootstrap-switch" onText="开" offText="关" handleWidth="50px" labelWidth="50px" switch-size="normal" onColor="success" offColor="warning" fun="comment_switch" switch="false">
                               </c:otherwise>
                           </c:choose>   
                        </li>
                        <li>
                           <span class="label label-required">字数限制(个): </span> 
			               <input type="text" class="width-122 text-align-center input-config" value="${systemConfig.comment_content_word_limit}" fun="comment_content_word_limit">
                        </li>
                        <li>
                           <span class="label label-required">表情数限制(个): </span> 
			               <input type="text" class="width-122 text-align-center input-config" value="${systemConfig.comment_content_img_limit}" fun="comment_content_img_limit">
                        </li>
                     </ul> 
                 </li>
                 <li>
                     <span class="label label-required">附件下载设置: </span> 
                     <ul class="tag-level2_2">
                        <li>
                           <span class="label label-required">开关: </span> 
                           <c:choose>
                               <c:when test="${systemConfig.attachment_download_switch=='on'}">
                                   <input type="checkbox" class="bootstrap-switch" onText="开" offText="关" handleWidth="50px" labelWidth="50px" switch-size="normal" onColor="success" offColor="warning" fun="attachment_download_switch" switch="true">
                               </c:when>
                             
                               <c:otherwise>
                                   <input type="checkbox" class="bootstrap-switch" onText="开" offText="关" handleWidth="50px" labelWidth="50px" switch-size="normal" onColor="success" offColor="warning" fun="attachment_download_switch" switch="false">
                               </c:otherwise>
                           </c:choose> 
                        </li>
                        <li>
                           <span class="label label-required">间隔限制(秒): </span> 
			               <input type="text" class="width-122 text-align-center input-config" value="${systemConfig.attachment_download_interval_limit}" fun="attachment_download_interval_limit">
                        </li>
                     </ul> 
                 </li>
                 <li>
                     <span class="label label-required">系统语言设置: </span> 
                     <ul class="tag-level2_2">
                        <li>
                           <span class="label label-required">默认语言: </span>
                           <c:choose>
                               <c:when test="${systemConfig.system_language_default=='en'}">
                                   <input type="checkbox" class="bootstrap-switch" onText="英文" offText="中文" handleWidth="50px" labelWidth="50px" switch-size="normal" onColor="success" offColor="primary" fun="system_language_default" switch="true">
                               </c:when>
                             
                               <c:otherwise>
                                   <input type="checkbox" class="bootstrap-switch" onText="英文" offText="中文" handleWidth="50px" labelWidth="50px" switch-size="normal" onColor="success" offColor="primary" fun="system_language_default" switch="false">
                               </c:otherwise>
                           </c:choose>  
                        </li>
                     </ul> 
                 </li>
                 <li>
                     <span class="label label-required">系统防护设置: </span> 
                     <ul class="tag-level2_2">
                        <li>
                           <span class="label label-required">非法参数阀值: </span> 
			               <input type="text" class="width-122 text-align-center input-config" value="${systemConfig.illegal_argument_limit}" fun="illegal_argument_limit">
                        </li>
                     </ul> 
                 </li>
              </ul>
           </div>
           
           <div class="clear-both height20"></div>
           <h4>说明:</h4>
           <p class="text-indent-30 margin-top10">当<span class="font-bold">评论设置</span>中的开关设置为开启后，用户方可发表留言。可以利用<span class="font-bold">字数限制</span>和<span class="font-bold">表情数限制</span>来限制评论中的字数和表情数。</p>
           <p class="text-indent-30 margin-top10">当<span class="font-bold">附件下载设置</span>中的开关设置为开启后，用户方可下载附件。可以利用<span class="font-bold">下载间隔限制</span>来限制两次附件下载的时间间隔，从而规避恶意附件下载攻击。</p>
           <p class="text-indent-30 margin-top10">可以利用<span class="font-bold">系统语言设置</span>来设置前台页面的默认语言显示</p>
           <p class="text-indent-30 margin-top10"><span class="font-bold">非法参数阀值</span>主要是预防用户修改http请求中的参数为非法参数来达到攻击目的，当在同一session会话中如果<span class="font-bold">非法参数阀值</span>达到阀值，将会将此访问ip拉入ip黑名单，从而规避恶意的http攻击。</p>
	   </div>
	</div>

    <input type="hidden" id="currentHref" value="systemConfig"/>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>