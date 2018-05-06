<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Yangtzeriver's Blog后台管理--缓存管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.css"/>
<link rel="stylesheet" href="<%=basePath%>css/admin/cacheManage.css"/>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/cacheManage.js"></script>
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
	       <h2 class="text-align-center">缓存管理</h2>
	       
           <div id="data-show">
                <a class="btn  btn-primary new-cache-manage" type="button">添加</a>
				
	            <table id="cacheManageList-table"></table>
           </div>
            
           <div id="empty-prompt">暂无数据.点击<a class="btn  btn-primary new-cache-manage" type="button">添加</a></div>            
	   </div>
	</div>
	
	<!-- 缓存添加 -->
	<div class="modal fade cache-manage-modal" id="cache-manage-modal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">缓存添加</h4>
		         </div>
		         
		         <div class="modal-body">
		           <form id="cache-manage-form">
			            <div class="form-inline">
						  <span class="label label-required">缓存code: </span> 
						  <input type="text" class="input-block-level" name="code"/>
			            </div>
			            
			            <div class="form-inline">
						  <span class="label label-required">缓存名称: </span> 
						  <input type="text" class="input-block-level" name="name"/>
			            </div>
			            
			            <div class="form-inline">
						  <span class="label label-required float-left">key策越: </span> 
		
						  <span class="float-left margin-top3">
							  <span class="pay_list_c1">
							     <input id="allEntries-true-new" class="opacityclass" value="true" name="allEntries" type="radio"/>
							  </span>
							  <label for="allEntries-true-new">启用</label>
					      </span>	
				          <span class="float-left margin-top3">
							  <span class="pay_list_c1">
							     <input id="allEntries-false-new" class="opacityclass" value="false" name="allEntries" type="radio"/>
							  </span>
							  <label for="allEntries-false-new">关闭</label>
					      </span>	
			            </div>
			            
			            <br/><br/>
			            <div class="form-inline clear-both">
						  <span class="label label-required">描述: </span>
						  <textarea class="input-block-level cache-descrition" name="descrition"></textarea> 
						</div>
				   </form>	  
		         </div>   
		         
		         <div class="modal-footer">
		            <span id="new-cache-manage-input-error-prompt" class="color-red float-left"></span>
		            <button type="button" class="btn btn-success" id="new-cache-manage-submit">添加</button>
		         </div>
		    </div>
		</div>
	</div>
	
	<!-- cache key策越值输入 -->
	<div class="modal fade" id="cache-key-input-modal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">key策越缓存刷新</h4>
		         </div>
		         
		         <div class="modal-body">
		            <div class="form-inline">
					  <span class="label label-required">cache key: </span> 
					  <input type="text" class="input-block-level" name="cache-key"/>
		            </div>  
		         </div>   
		         
		         <div class="modal-footer">
		            <span id="cache-key-input-error-prompt" class="color-red float-left"></span>
		            <button type="button" class="btn btn-success" id="cache-key-input-submit" cache-code="">刷新缓存</button>
		         </div>
		    </div>
		</div>
	</div>
	
	<!-- cache manage update -->
	<div class="modal fade" id="cache-manage-update-modal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">缓存设置</h4>
		         </div>
		         
		         <div class="modal-body">
			            <div class="form-inline">
						  <span class="label label-required">缓存code: </span> 
						  <input type="text" class="input-block-level" name="code"/>
			            </div>
			            
			            <div class="form-inline">
						  <span class="label label-required">缓存名称: </span> 
						  <input type="text" class="input-block-level" name="name"/>
			            </div>
			            
			            <div class="form-inline">
						  <span class="label label-required float-left">key策越: </span> 
		
						  <span class="float-left margin-top3">
							  <span class="pay_list_c1">
							     <input id="allEntries-true-update" class="opacityclass" value="true" name="allEntries" type="radio"/>
							  </span>
							  <label for="allEntries-true-update">启用</label>
					      </span>	
				          <span class="float-left margin-top3">
							  <span class="pay_list_c1">
							     <input id="allEntries-false-update" class="opacityclass" value="false" name="allEntries" type="radio"/>
							  </span>
							  <label for="allEntries-false-update">关闭</label>
					      </span>	
			            </div>
			            
			            <br/><br/>
			            <div class="form-inline clear-both">
						  <span class="label label-required">描述: </span>
						  <textarea class="input-block-level cache-descrition" name="descrition"></textarea> 
						</div>
		         </div>   
		         
		         <div class="modal-footer">
		            <span id="cache-manage-update-input-error-prompt" class="color-red float-left"></span>
		            <button type="button" class="btn btn-success" id="cache-manage-update-submit" cache-id="" cache-code="" cache-name="" cache-allEntries="" cache-descrition="">更新设置</button>
		         </div>
		    </div>
		</div>
	</div>

    <input type="hidden" id="currentHref" value="chcheManage"/>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>