<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Yangtzeriver's Blog后台管理--文章列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<jsp:include page="/WEB-INF/import_jsCss.jsp" /> 
<link rel="stylesheet" href="<%=basePath%>css/admin/documentList.css"/>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.css"/>
<script type="text/javascript" src="<%=basePath%>js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/documentList.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-table/1.10.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/plupload/config.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/plupload/carouselConfig.js"></script>
</head>
<body>

    <jsp:include page="topMenu.jsp"/>
    
	<div class="container-fluid containerb margin-top70">
		<div class="row-fluid">
            <!-- 主体内容填充 -->
            <h2 class="text-align-center">文章查询</h2>
            
            <div class="search">    
                 <div class="search-content">
	                 <div class="form-inline">
				          <span class="leftpart">
							<span class="label label-required">文章名称: </span> 
							<input class="form-control" name="document-title"  type="text"/>
						  </span>
						  
						  <span class="rightpart" >	
						    <span class="label label-required">文章标签: </span> 
				            <input class="form-control" name="document-tags"  type="text"/> 
						  </span>
						  <div class="clear-both"></div>	 
				     </div>
				     
				     <div class="form-inline">
				          <span class="leftpart">
								<span class="label label-required">创建日期: </span> 
								 
								<input class="form-control Wdate" name="createTime" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
								至
								<input class="form-control Wdate" name="endTime" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						  </span> 
						  
						  <span class="rightpart margin-right86">
						        <span class="label label-required float-left">文档状态: </span>
								 
								<span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="all_status" class="opacityclass"  name="documentStatus" value=""  type="radio" checked="checked"/>
									</span>
									<label for="all_status">全部 </label>
						        </span>
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="normal_status" class="opacityclass" name="documentStatus" value="0"  type="radio"/>
									</span>
									<label for="normal_status">正常 </label>
						        </span>	
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="disabled_status" class="opacityclass" name="documentStatus" value="1"  type="radio"/>
									</span>
									<label for="disabled_status">失效</label>
						        </span>
						  </span>
						  <div class="clear-both"></div>	
				     </div>
				     
				     <div class="form-inline">
						  <span class="leftpart">
						        <span class="label label-required float-left">文档类型: </span>
								 
								<span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="all_type" class="opacityclass"  name="documentType" value=""  type="radio" checked="checked"/>
									</span>
									<label for="all_type">全部 </label>
						        </span>
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="hot_type" class="opacityclass" name="documentType" value="hot"  type="radio"/>
									</span>
									<label for="hot_type">热门 </label>
						        </span>	
					            <span class="float-left margin-top3">
									<span class="pay_list_c1">
									   <input id="top_type" class="opacityclass" name="documentType" value="top"  type="radio"/>
									</span>
									<label for="top_type">置顶</label>
						        </span>
						  </span>
						  <div class="clear-both"></div>	
				     </div>
				     
				     <div class="margin-auto-150 margin-top30">
						  <button class="btn btn-block btn-primary" id="document-search-button">查询</button>
				     </div>
				 </div>    
            </div>
            
            <div id="data-show">
               <div class="btn-group operation_bar"> 
	              <a class="btn  btn-primary batch-hot-operation" type="button" operation="1">批量加热</a>
	              <a class="btn  btn-success batch-hot-operation" type="button" operation="0">批量解热</a>
				</div>
				<div class="btn-group operation_bar"> 
	              <a class="btn  btn-warning batch-use-operation" type="button" operation="1">批量禁用</a>
	              <a class="btn  btn-success batch-use-operation" type="button" operation="0">批量解禁</a>
				</div>
				
	            <table id="documentList-table"></table>
            </div>
            
            <div id="empty-prompt">暂无数据</div>   
            <br/>
	   </div>
	</div>
	
	
	<!-- 附件管理对话框 -->
	<div class="modal fade attachment-modal modal-show-control" id="attachment-modal" tabindex="-1" role="dialog" aria-labelledby="attachment-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title attachment-title">附件管理</h4>
		            <div class="document-title-show" id="attachment-document-title"></div>
		         </div>
		         
		         <div class="modal-body attachment" id="attachment-modal-body">
		            <table class="attachment-table">
		               <thead> 
		                  <th>附件名</th>
		                  <th>附件大小</th>
		                  <th>上传进度</th>
		                  <th>状态</th>
		                  <th>上传时间</th>
		                  <th>下载次数</th>
		                  <th>操作</th>
		               </thead>
		               
		               <tbody id="attachment-table-tbody">
		                  
		               </tbody>
		            </table>
		            
		            <div class="color-red" id="upload-prompt">Your browser doesn't have Flash, Silverlight or HTML5 support</div>
		         </div>
		         
		         <div class="modal-footer">
		            <button type="button" class="btn btn-warning" id="remove-upload-queue">移除上传队列</button>
		            <button type="button" class="btn btn-primary" id="select-file">选择文件</button>
		            <button type="button" class="btn btn-success" id="upload-file">确认上传</button>
		         </div>
		    </div>
		</div>
		<input type="hidden" id="attachment-docId"/>
	</div>
	
	<!-- 评论对话框 -->
	<div class="modal fade comment-list-modal modal-show-control" id="comment-list-modal" tabindex="-1" role="dialog" aria-labelledby="comment-list-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title comment-list-title" id="comment-list-modal-label"></h4>
		         </div>
		         
		         <div class="modal-body comment-list" id="comment-list-modal-body"></div>
		    </div>
		</div>
	</div>
	
	<!-- 置顶轮播图对话框 -->
	<div class="modal fade carousel-modal modal-show-control" id="carousel-modal" tabindex="-1" role="dialog" aria-labelledby="carousel-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		 <div class="modal-dialog">
		    <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		            <h4 class="modal-title">置顶轮播图操作</h4>
		         </div>
		         
		         <div class="modal-body" id="carousel-modal-tbody">
		            <div id="upload-carousel-prompt"><span class="color-red">Your browser doesn't have Flash, Silverlight or HTML5 support</span></div>
		         </div>
		         
		         <div class="modal-footer">
		            <button type="button" class="btn btn-warning" id="remove-carousel-upload-queue">移除上传队列</button>
		            <button type="button" class="btn btn-primary" id="select-carousel">选择轮播图片</button>
		            <button type="button" class="btn btn-success" id="upload-carousel">确认上传</button>
		         </div>
		    </div>
		</div>
		<input type="hidden" id="carousel-docId"/>
		<input type="hidden" id="carousel-rowIndex"/>
	</div>

    <input type="hidden" id="currentHref" value="documentList"/>
    <jsp:include page="../WEB-INF/footer.jsp"/>
</body>
</html>