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
    <title>精诚教育</title>
    
    <jsp:include page="import_jsCss.jsp" />  
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/index.css">
    <script type="text/javascript" src="<%=basePath%>js/index.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/touchwipe.js"></script>
  </head>
  <body>
      
      <c:if test="${accessFlag=='no'}">
         <div class="introduce linear-gradient">
            <div class="show">
               <h1 class="hang font-bold auto-min-hang">${languageConfigParameter.blog_common_topicTitle}</h1>
               <h3 class="hang">${languageConfigParameter.blog_index_leader_one}</h3>
               <h3 class="hang">${languageConfigParameter.blog_index_leader_two}</h3>
	       </div>    
	       
	       <div class="scroll-down">&gt;</div> 
         </div>
      </c:if>
      
      
	  <div class="mycontainer" id="mainContent">
	       <jsp:include page="topMenu.jsp"/>
	       
	       <div class="containerb row-fluid show">
		      <div class="span8 left-main animated fadeInLeft">
		         
			     <!-- 课程展示 -->
		         <div class="panel hot-doc">
					   <div class="panel-heading">
					     <h3 class="panel-title">我的课程</h3>
					   </div>
					  
					   <div class="panel-body"> 
					   		<c:choose>
								<c:when test="${!empty courseSignList && fn:length(courseSignList)>0}">
								    <table>
								       <tr>
								       		<td>课程名称</td>
								       		<td>报名时间</td>
								       		<td>当前进度</td>
								       </tr>
								       
								       <c:forEach var="courseSign" items="${courseSignList}">
								          <tr>
								       		<td><a href="<%=basePath %>detail.do?documentId=${courseSign.courseId}" target="_blank">${courseSign.courseName}</a></td>
								       		<td>${courseSign.createDate}</td>
								       		<td>${courseSign.progress}%</td>
								          </tr>
								    </c:forEach>
								    </table>
								</c:when>
								
								<c:otherwise>
								  <div class="non-data-prompt">${languageConfigParameter.blog_common_empty_data_prompt}</div>
								</c:otherwise>
					        </c:choose>	
					   </div>
			     </div>	   
		        
		        <div class="pager-clear"></div>
		      </div>
		   </div>   
		   <jsp:include page="footer.jsp"/>
	   </div>
	   <input type="hidden" id="currentHref" value="myCourse"/>
  </body>
</html>