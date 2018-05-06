<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Yangtzeriver's Blog后台管理--Tags</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
<link rel="stylesheet" href="<%=basePath%>css/admin/documentList.css"/>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.css"/>
<script type="text/javascript" src="<%=basePath%>js/admin/tags.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/locale/bootstrap-table-zh-CN.min.js"></script>
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
            <!-- 主体内容填充 -->
            <h2 class="text-align-center">标签管理</h2>
            
            <div class="search" id="tag-search-form">    
                 <div class="search-content">
	                 <div class="form-inline">
				          <span class="leftpart">
							<span class="label label-required">标签名称: </span> 
							<input class="form-control" name="tag-code"  type="text"/>
						  </span>
						  
						  <span class="rightpart" >	
						    <span class="label label-required">所属一级标签: </span> 
				            <input class="form-control" name="tag-oneLevelCode"  type="text"/> 
						  </span>
						  <div class="clear-both"></div>	 
				     </div>
				     
				     <div class="form-inline">
				          <span class="leftpart">
								<span class="label label-required">创建日期: </span> 
								 
								<input class="form-control Wdate" name="beginTime" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
								至
								<input class="form-control Wdate" name="endTime" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						  </span> 
						  
						  <span class="rightpart margin-right86">
						        <span class="label label-required float-left">标签等级: </span>
								 
								<span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="all_level_search" class="opacityclass"  name="tag-level" value=""  type="radio" checked="checked"/>
									</span>
									<label for="all_level_search">全部 </label>
						        </span>
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="level_one_search" class="opacityclass" name="tag-level" value="1"  type="radio"/>
									</span>
									<label for="level_one_search">一级 </label>
						        </span>	
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="level_two_search" class="opacityclass" name="tag-level" value="2"  type="radio"/>
									</span>
									<label for="level_two_search">二级</label>
						        </span>
						  </span>
						  <div class="clear-both"></div>	
				     </div>				
				     
				     <div class="margin-auto-150 margin-top30">
						  <button class="btn btn-block btn-primary" id="tag-search-button">查询</button>
				     </div>
				 </div>    
            </div>
            
            <div id="data-show">
                <div class="btn-group"> 
	              <a class="btn  btn-primary add-tag-modal" type="button">新增</a>
	              <a class="btn  btn-success" type="button" id="tags-delete">删除</a>
				</div>
				
	            <table id="tags-table"></table>
            </div>
            
            <div id="empty-prompt">暂无数据,点击<a href="javascript:void(0)" class="add-tag-modal">新增标签</a></div>   
            <br/>
	   </div>
	</div>
	
	<!-- 新增标签对话框 -->
	<div class="modal fade tags-modal modal-show-control" id="tags-modal" tabindex="-1" role="dialog" aria-labelledby="tags-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">新增标签</h4>
		         </div>
		         
		         <div class="modal-body" id="add-tag-form">
		             <div class="form-inline">
					    <span class="label label-required">标签名称: </span> 
					    <input type="text" class="input-block-level blurcheck" non-empty non-empty-prompt="标签名称不能为空"  data-toggle="tooltip" data-placement="top" data-trigger="manual" name="tag-code"/>
				     </div>
				     <div class="form-inline">
					    <span class="label label-required float-left">标签等级: </span>
			            <span class="float-left margin-top3">
							<span class="pay_list_c1">
							   <input id="level_one" class="input-block-level opacityclass" non-empty non-empty-prompt="请选择标签"  data-toggle="tooltip" data-placement="top" data-trigger="manual" name="tag-level" value="1"  type="radio"/>
							</span>
							<label for="level_one">一级</label>
				        </span>	
			            <span class="float-left margin-top3">
							<span class="pay_list_c1">
							   <input id="level_two" class="input-block-level opacityclass" non-empty non-empty-prompt="请选择标签"  data-toggle="tooltip" data-placement="top" data-trigger="manual" name="tag-level" value="2"  type="radio"/>
							</span>
							<label for="level_two">二级</label>
				        </span>
				        <div class="clear-both"></div>	
				     </div>
				     <div class="form-inline">
					    <span class="label label-required">所属一级标签: </span> 
					    <input type="text" class="input-block-level" name="tag-oneLevelCode"/>
				     </div>
		         </div>
		         
		         <div class="modal-footer">
		            <button type="button" class="btn btn-warning" data-dismiss="modal">close</button>
		            <button type="button" class="btn btn-success" id="add-tag-submit">submit</button>
		         </div>
		    </div>
		</div>
	</div>
	
    <input type="hidden" id="currentHref" value="tags"/>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>