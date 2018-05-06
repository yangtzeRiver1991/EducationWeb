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
<link rel="stylesheet" href="<%=basePath%>css/admin/ipManage.css"/>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/ipManage.js"></script>
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
	       <h2 class="text-align-center">IP访问权限管理</h2>
	       
	       <div class="search">    
                 <div class="search-content">    
				     <div class="form-inline">
				          <span class="leftpart">
							<span class="label label-required">IP: </span> 
							<input class="form-control" id="ip"  type="text"/>
						  </span>
						  
						  <span class="rightpart margin-right86">
						        <span class="label label-required float-left">IP状态: </span>
								 
								<span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="all_status" class="opacityclass"  name="ipStatus" value=""  type="radio" checked="checked"/>
									</span>
									<label for="all_status">全部 </label>
						        </span>
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="normal_status" class="opacityclass" name="ipStatus" value="0"  type="radio"/>
									</span>
									<label for="normal_status">正常 </label>
						        </span>	
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="disabled_status" class="opacityclass" name="ipStatus" value="1"  type="radio"/>
									</span>
									<label for="disabled_status">黑名单</label>
						        </span>
						  </span>
						  <div class="clear-both"></div>	
				     </div>

				     <div class="margin-auto-150 margin-top30">
						  <button class="btn btn-block btn-primary" id="ip-search-button">查询</button>
				     </div>
				 </div>    
            </div>
	       
           <div id="data-show">
                <a class="btn  btn-primary new-ip-black" type="button">新增IP黑名单</a>
				
				<div class="btn-group operation_bar"> 
	              <a class="btn  btn-info batch-ip-operation" type="button" operation="1">批量拉黑名单</a>
	              <a class="btn  btn-success batch-ip-operation" type="button" operation="0">批量恢复正常</a>
				</div>
				
	            <table id="ip-info-list-table"></table>
           </div>
            
           <div id="empty-prompt">暂无数据.点击<a class="btn  btn-primary new-ip-black" type="button">新增IP黑名单</a></div>            
	   </div>
	</div>
	
	<!-- 新增IP黑名单 -->
	<div class="modal fade ip-manage-modal" id="ip-manage-modal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">新增IP黑名单</h4>
		         </div>
		         
		         <div class="modal-body">
		           <form id="ip-manage-form">
			            <div class="form-inline">
						  <span class="label label-required">IP: </span> 
						  <input type="text" class="input-block-level" name="ip"/>
			            </div>

			            <div class="form-inline clear-both">
						  <span class="label label-required">禁用原因: </span>
						  <textarea class="input-block-level ip-limit-descrition" name="descrition"></textarea> 
						</div>
				   </form>	  
		         </div>   
		         
		         <div class="modal-footer">
		            <span id="new-ip-manage-input-error-prompt" class="color-red float-left"></span>
		            <button type="button" class="btn btn-success" id="new-ip-manage-submit">添加</button>
		         </div>
		    </div>
		</div>
	</div>
	
	<!-- 日志浏览 -->
	<div class="modal fade ip-descrition-modal" id="ip-descrition-modal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title"></h4>
		         </div>
		         
		         <div class="modal-body ip-descrition-show">
  
		         </div>   
		    </div>
		</div>
	</div>
	
	<!-- 备注说明填写 -->
	<div class="modal fade ip-descrition-input-modal" id="ip-input-descrition-modal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">操作备注说明填写</h4>
		         </div>
		         
		         <div class="modal-body">
                        <div class="form-inline clear-both">
						  <span class="label label-required">备注: </span>
						  <textarea class="input-block-level ip-operation-descrition" name="descrition"></textarea> 
						</div>
		         </div> 
		         
		         <div class="modal-footer">
		            <span id="ip-descrition-input-error-prompt" class="color-red float-left"></span>
		            <button type="button" class="btn btn-success" id="ip-descrition-submit">提交</button>
		         </div>  
		    </div>
		</div>
	</div>

    <input type="hidden" id="currentHref" value="ipManage"/>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>