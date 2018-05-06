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
		         <c:if test="${!empty topDocuments && fn:length(topDocuments)>0}">
			         <div class="carousel slide img-carousel animated fadeInDown" id="myCarousel">
			             <ol class="carousel-indicators">
			                 <c:forEach var="topDocumentIndex" begin="1" end="${fn:length(topDocuments)}">
			                   <c:choose>
			                     <c:when test="${topDocumentIndex>1}">
			                        <li data-target="#myCarousel" data-slide-to="${topDocumentIndex}"></li>
			                     </c:when>
			                     <c:otherwise>
			                        <li data-target="#myCarousel" data-slide-to="${topDocumentIndex}" class="active"></li>
			                     </c:otherwise>
			                   </c:choose>
			                 </c:forEach>
						  </ol>
			              <div class="carousel-inner">
			                <c:forEach var="topDocument" items="${topDocuments}" varStatus="status">
			                   <c:choose>
			                     <c:when test="${status.index >0}">
			                         <div class="item" documentId="${topDocument.id}">
					                   <reponseimg src="${topDocument.carouselImg}"></reponseimg>
					                   <div class="carousel-caption">
					                     <h4 class="long-text-limit">${topDocument.title}</h4>
					                     <p class="long-text-limit">${topDocument.intro}</p>
					                   </div>
					                 </div>
			                     </c:when>
			                     <c:otherwise>
			                        <div class="item active" documentId="${topDocument.id}">
					                   <reponseimg src="${topDocument.carouselImg}"></reponseimg>
					                   <div class="carousel-caption">
					                     <h4 class="long-text-limit">${topDocument.title}</h4>
					                     <p class="long-text-limit">${topDocument.intro}</p>
					                   </div>
					                 </div>
			                     </c:otherwise>
			                   </c:choose>
			                 </c:forEach>
			              </div>
			              <a data-slide="prev" href="#myCarousel" class="left carousel-control carousel-control-hide">‹</a>
			              <a data-slide="next" href="#myCarousel" class="right carousel-control carousel-control-hide">›</a>
			         </div>
		         </c:if>
		      
		         <!-- 新闻展示 -->
		         <div class="panel hot-doc">
					   <div class="panel-heading">
					     <h3 class="panel-title">新闻列表</h3>
					   </div>
					  
					   <div class="panel-body"> 
					   		<c:choose>
								<c:when test="${!empty newDocuments && fn:length(newDocuments)>0}">
								    <c:forEach var="document" items="${newDocuments}">
								       <p>
					                       <a href="<%=basePath %>detail.do?documentId=${document.id}" target="_blank"> 
					                          ${document.title} &nbsp;&nbsp;&nbsp;&nbsp;${document.createTime}&nbsp;&nbsp;&nbsp;&nbsp;${document.tags}
					                       </a>
								       </p>
								    </c:forEach>
								</c:when>
								
								<c:otherwise>
								  <div class="non-data-prompt">${languageConfigParameter.blog_common_empty_data_prompt}</div>
								</c:otherwise>
					        </c:choose>	
					   </div>
			     </div>	
			     
			     <!-- 课程展示 -->
		         <div class="panel hot-doc">
					   <div class="panel-heading">
					     <h3 class="panel-title">选课中心</h3>
					   </div>
					  
					   <div class="panel-body"> 
					   		<c:choose>
								<c:when test="${!empty courseDocuments && fn:length(courseDocuments)>0}">
								    <c:forEach var="document" items="${courseDocuments}">
								       <p>
					                       <a href="<%=basePath %>detail.do?documentId=${document.id}" target="_blank"> 
					                          ${document.title} &nbsp;&nbsp;&nbsp;&nbsp;${document.createTime}&nbsp;&nbsp;&nbsp;&nbsp;${document.tags}
					                       </a>
								       </p>
								    </c:forEach>
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
		   <input type="hidden" id="currentHref" value="index"/>
	   </div>
  </body>
</html>