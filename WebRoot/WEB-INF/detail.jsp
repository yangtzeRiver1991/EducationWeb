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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/wangEditor/wangEditor.min.css">
    <script type="text/javascript" src="<%=basePath%>js/detail.js"></script>
  </head>
  <body>
	   <div class="mycontainer" id="mainContent" style="display: block;">
	       <jsp:include page="topMenu.jsp"/>
	       
	       <div class="containerb row-fluid show">
		      <div class="span8 left-main">
		         <div class="doc-detail">
		            <h1 class="doc-title">${document.title}</h1>
					               
					<div class="doc-content">${document.content}</div>
					
					<!-- 如果是课程，则可以点击报名该课程 -->
					<c:if test="${fn:contains(document.tags,'课程')}">
						<button class="btn  btn-primary" id="courseSign" data-course-id="${document.id}" data-course-name="${document.title}">我要报名该课程</button>	
						<input type="hidden" id="currentHref" value="courseCenter"/>						
					</c:if>
					
					<c:if test="${fn:contains(document.tags,'新闻')}">
						<input type="hidden" id="currentHref" value="news"/>
					</c:if>
				    
				    <div class="clear-both"></div>
				    
				    <c:if test="${!empty document.attachments && fn:length(document.attachments)>0}">
				       <div class="attachment-list">  
					       <div class="attachment-list-title">
					           ${languageConfigParameter.blog_index_attachment_download}(<span class="attachment-num">${fn:length(document.attachments)}</span>)
					       </div>
					       
					       <div class="attachment-list-content">
					          <c:forEach var="attachment" items="${document.attachments}">
					             <div class="attachment-detail">
					               <span class="attachment-detail-download" code="${attachment.code}">${attachment.name}(${attachment.size}${attachment.unit}&nbsp;&nbsp;${attachment.downloadCount}&nbsp;${languageConfigParameter.blog_index_attachment_download_time})</span>
					               <span class="attachment-detail-download-prompt"></span>
					             </div>
					          </c:forEach>
					       </div>
					   </div>
					   
					   <div class="height5 clear-both"></div>    
				    </c:if>
				    
				    <div class="comment-list-title">
				         ${languageConfigParameter.blog_index_comment_commentview}(<span class="comment-num">${document.commentList.total}</span>)&nbsp;|&nbsp;
				         <a href="javascript:void(0)" class="checked sort-link-a" sort="default">${languageConfigParameter.blog_index_comment_sort_default}</a>&nbsp;|&nbsp;<a href="javascript:void(0)" class="sort-link-a" sort="new">${languageConfigParameter.blog_index_comment_sort_desc}</a>
				    </div>
				    
				    <div class="comment-list">
				       <c:choose>
				           <c:when test="${!empty document.commentList.rows && fn:length(document.commentList.rows)>0}">
				                <c:forEach var="comment" items="${document.commentList.rows}" varStatus="status">
				                    <div class="comment-detail">
				                        <div class="comment-detail-title">
				                           <c:choose>
				                              <c:when test="${document.commentList.sort=='asc'}">
				                                  <span class="floor-num">#${(document.commentList.nowPageNum-1) * document.commentList.pageSize + status.index +1}</span>${languageConfigParameter.blog_index_comment_floor}
				                              </c:when>
				                              <c:otherwise>
				                                  <span class="floor-num">#${document.commentList.total - (document.commentList.nowPageNum-1)*document.commentList.pageSize - status.index}</span>${languageConfigParameter.blog_index_comment_floor}
				                              </c:otherwise>
				                           </c:choose>
				                           
				                           <span class="comment-user">${comment.commentUser}</span>&nbsp;${languageConfigParameter.blog_index_comment_at}&nbsp;<span class="comment-date">${comment.commentDate}</span>&nbsp;${languageConfigParameter.blog_index_comment_lower}
				                        </div>
				                        
				                        <c:choose>
				                           <c:when test="${comment.status==0}">
				                                <div class="comment-detail-content">${comment.commentContent}</div>
				                                
				                                <div class="comment-other-operation">
				                                  <c:choose>
				                                     <c:when test="${comment.agreeNum>0}">
				                                        <span class="comment-agree" commentId="${comment.id}"><i class="icon-hand-up"></i>${languageConfigParameter.blog_index_comment_agree}<span class="color-red show-num" show-num="${comment.agreeNum}">(${comment.agreeNum})</span></span>
				                                     </c:when>
				                                     
				                                     <c:otherwise>
				                                        <span class="comment-agree" commentId="${comment.id}"><i class="icon-hand-up"></i>${languageConfigParameter.blog_index_comment_agree}<span class="color-red show-num" show-num="${comment.agreeNum}"></span></span>
				                                     </c:otherwise>
				                                  </c:choose>
				                                  
				                                  <c:choose>
				                                     <c:when test="${comment.againstNum>0}">
				                                        <span class="comment-against" commentId="${comment.id}"><i class="icon-hand-down"></i>${languageConfigParameter.blog_index_comment_against}<span class="color-red show-num" show-num="${comment.agreeNum}">(${comment.againstNum})</span></span>
				                                     </c:when>
				                                     
				                                     <c:otherwise>
				                                        <span class="comment-against" commentId="${comment.id}"><i class="icon-hand-down"></i>${languageConfigParameter.blog_index_comment_against}<span class="color-red show-num" show-num="${comment.agreeNum}"></span></span>
				                                     </c:otherwise>
				                                  </c:choose>
				                                </div>
				                           </c:when>
				                           
				                           <c:otherwise>
				                                <div class="comment-disabled-content">${languageConfigParameter.blog_index_comment_disabled_prompt}</div>
				                           </c:otherwise>
				                        </c:choose>
				                    </div>
				                </c:forEach>
				                
				                <c:if test="${document.commentList.allPageNum>1}">
				                    <ul class="pager">
							          <c:choose>
							             <c:when test="${document.commentList.nowPageNum==1}">
							                <li class="previous disabled"><a href="javascript:void(0)">${languageConfigParameter.blog_index_pager_previous}&larr;</a></li>
							             </c:when>
							             <c:otherwise>
							                <li class="previous page-button" nowPageNum="${document.commentList.nowPageNum-1}"><a href="javascript:void(0)">${languageConfigParameter.blog_index_pager_previous}&larr;</a></li>
							             </c:otherwise>
							          </c:choose>
									  
									  <c:choose>
							             <c:when test="${document.commentList.nowPageNum==document.commentList.allPageNum}">
							                <li class="next disabled"><a href="javascript:void(0)">&rarr;${languageConfigParameter.blog_index_pager_next}</a></li>
							             </c:when>
							             <c:otherwise>
							                <li class="next page-button" nowPageNum="${document.commentList.nowPageNum+1}"><a href="javascript:void(0)">&rarr;${languageConfigParameter.blog_index_pager_next}</a></li>
							             </c:otherwise>
							          </c:choose>
								    </ul>
								    <div class="quick-pager-link2">
								        <div class="quick-pager-show">
								           <span class="pager-select" pagernum="${document.commentList.allPageNum}">${languageConfigParameter.blog_index_pager_no}&nbsp;<span class="num">${document.commentList.nowPageNum}</span>&nbsp;<i class="icon-chevron-down"></i>&nbsp;&nbsp;</span>|&nbsp;&nbsp;${languageConfigParameter.blog_index_pager_all}&nbsp;<span class="num">${document.commentList.allPageNum}</span>&nbsp;${languageConfigParameter.blog_index_pager_all_page}
								        </div>
								    </div>
				                </c:if>
				           </c:when>
				           <c:otherwise>
				              <div class="empty-comment">${languageConfigParameter.blog_index_comment_empty}!</div>
				           </c:otherwise>
				       </c:choose>
				    </div>
				    
				    <div class="comment-dialog-title">${languageConfigParameter.blog_index_comment_upper}</div>
				    <div class="comment-dialog">
				        <div id="editor" style="height:200px;max-height:200px;">
						</div>
				    </div>
				    <div class="comment-dialog-prompt"></div>
				    
				    <div class="comment-dialog-validate-code form-inline">
				            ${languageConfigParameter.blog_index_label_validate_code}:
				            <input name="verificationCode" type="text" class="form-control verification-code-input" id="comment-verificationCode"/>
				            <img src="<%=basePath%>getVerificationCode.do" class="verificationCodeImg" style="top:-8px;"/>
				     </div>
				     
				    <button class="btn  btn-primary comment-button-submit">${languageConfigParameter.blog_index_button_submit}</button>	
				    <div style="height: 30px;"></div>							
		         </div>
		      </div>
		      
		      <jsp:include page="rightShow.jsp"/>
		   </div>   
		   
		   <jsp:include page="footer.jsp"/>
	   </div>
	   
	   <form action="<%=basePath%>downloadAttachment.do" method="post" id="attachment-download-form" class="hidden-form">
	      <input type="hidden" name="code"/>
	   </form>
	   
	   <input type="hidden" id="docId" value="${document.id}"/>
	   <input type="hidden" id="sort" value="default"/>
	   <input type="hidden" id="documentTitle" value="${document.title}"/>
	   <script type="text/javascript" src="<%=basePath%>js/wangEditor/wangEditor.min.js"></script>
       <script type="text/javascript" src="<%=basePath%>js/wangEditor/editorConfig.js"></script>
  </body>
</html>