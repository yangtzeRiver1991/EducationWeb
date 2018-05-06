<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="span4 right-bar">
   <div class="animated fadeInRight">

      <jsp:include page="common_part_aboutme.jsp"/>    
      
      
      <jsp:include page="common_part_hotdocs.jsp"/> 
      
      <div class="panel clound-tag">
         <div class="panel-heading">
           <h3 class="panel-title">${languageConfigParameter.blog_index_topmenu_menu_ten}</h3>
         </div>
        
         <div class="panel-body clound"> 
              <div class="content-show" id="clound-content">
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="server" data-tag="tomcat">tomcat</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="server" data-tag="jboss">jboss</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="server" data-tag="nginx">nginx</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="bootstrap">bootstrap</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="jqueryEasyui">jquery easyui</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="java" data-tag="ehcache">ehcache</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="html5">html5</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="css3">css3</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="webindex" data-tag="blockUi">blockUI</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="system" data-tag="ubuntu">ubuntu</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="tools" data-tag="eclipse">eclipse</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="tools" data-tag="sublime">sublime</a>
				   <a href="javascript:void(0)" target="_self" class="hot-tag-link" data-parent-tag="tools" data-tag="notepad">notepad++</a>
			  </div>	
         </div>
      </div>  
      
      <div class="margin-top30">
          <jsp:include page="common_part_friendlylink.jsp"/>
      </div>
   </div>   
</div>