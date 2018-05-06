<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="panel hot-doc">
   <div class="panel-heading">
     <h3 class="panel-title">${languageConfigParameter.blog_index_topmenu_menu_nine}</h3>
   </div>
  
   <div class="panel-body"> 
        <c:choose>
			<c:when test="${!empty hotDocuments && fn:length(hotDocuments)>0}">
			    <c:forEach var="document" items="${hotDocuments}">
			       <p>
                       <a href="javascript:void(0)" title="${document.title}" class="hot-doc-link long-text-limit" documentId="${document.id}">${document.title}</a>
			       </p>
			    </c:forEach>
			</c:when>
			
			<c:otherwise>
			  <div class="non-data-prompt">${languageConfigParameter.blog_common_empty_data_prompt}</div>
			</c:otherwise>
        </c:choose>			
   </div>
</div> 