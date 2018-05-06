$(document).ready(function(){
	 initTable();
	 
	 $("#document-search-button").click(function(){
		 $("#documentList-table").bootstrapTable('refresh',{url:path+"admin/documentList.do?T=1"+setSearchParams()});
	 });
	 
	 $(".hot-link-button").live("click",function(){
		 var rowIndexArray = new Array($(this).attr("rowindex"));
		 var documentIdArray = new Array($(this).attr("documentId"));
		 var operation = $(this).attr("operation");

		 hotOperation(rowIndexArray,documentIdArray,operation);
	 });
	 
	 $(".top-link-button").live("click",function(){
		 var rowIndex = $(this).attr("rowindex");
		 var documentId = $(this).attr("documentId");
		 var operation = $(this).attr("operation");
		 
		 if(operation==1){
			 $("#carousel-docId").val(documentId);
			 $("#carousel-rowIndex").val(rowIndex);
			 $("#carousel-modal").modal("show");
		 }else{
			 $.ajax({
		            type: "POST",
		            url: path+"admin/abandonTop.do",
		            data: {
		           	  "documentId":documentId
		            },
		            dataType: "json",
		            success: function(data){
		           	 if(data.status==0){   
		          		$("#documentList-table").bootstrapTable('updateRow', {  
	              		    index: rowIndex,  
	              		    row: {  
	              		    	isTop:0
	              		    }  
		              	});
		           		
		           		alert("操作成功");
		           		$("#documentList-table").bootstrapTable('uncheckAll');
		           	 }else{
		           		 alert(data.msg);
		           	 }
		           	 
		            },
		            error:function(XMLHttpRequest, textStatus, errorThrown){
		           	     alert("系统繁忙，请稍候再试");
		            }
			 });
		 }

	 });
	 
	 $(".use-link-button").live("click",function(){
		 var rowIndexArray = new Array($(this).attr("rowindex"));
		 var documentIdArray = new Array($(this).attr("documentId"));
		 var operation = $(this).attr("operation");
		 
		 useOperation(rowIndexArray,documentIdArray,operation);
	 });
	 
	 $(".comment-link-button").live("click",function(){
		 var documentId = $(this).attr("documentId");
		 var documentTitle = $(this).attr("documentTitle");
		 
		 $.ajax({
	            type: "POST",
	            url: path+"admin/commentList.do",
	            data: {
	           	  "docId":documentId,
	           	  "nowPageNum":1,
	           	  "sort":"default"
	            },
	            dataType: "json",
	            success: function(data){
	           	 if(data.status==0){
	           		 if(data.result.pageResult.total>0){
	           			 resetCommentPage(data.result.pageResult,documentTitle,documentId);
		           		 $("#comment-list-modal").modal("show");
	           		 }else{
	           			 alert("暂无评论");
	           		 }
	           	 }else{
	           		 alert(data.msg);
	           	 }
	           	 
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 });
	 
	 $(".attachment-delete-link").live("click",function(){
		 var attachmentId = $(this).attr("attachmentId");
		 var code = $(this).attr("code");
		 var nowButton = $(this);
		 
		 $.ajax({
	            type: "POST",
	            url: path+"admin/deleteDocAttachment.do",
	            data: {
	           	  "attachmentId":attachmentId,
	           	  "code":code
	            },
	            dataType: "json",
	            success: function(data){
	               if(data.status==0){
	            	   nowButton.parent().parent().remove();
	            	   alert("删除成功");
	               }else{
	            	   alert("删除失败");
	               }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 });
	 
	 $(".attachment-link-button").live("click",function(){
		 var documentId = $(this).attr("documentId");
		 var documentTitle = $(this).attr("documentTitle");
		 $("#attachment-docId").val(documentId);
		 $("#attachment-document-title").html("---by<span class='doc-title'>&lt;&lt;"+documentTitle+"&gt;&gt;</span>");
		 
		 $.ajax({
	            type: "POST",
	            url: path+"admin/findDocAttachment.do",
	            data: {
	           	  "documentId":documentId
	            },
	            dataType: "json",
	            success: function(data){
		           	 if(data.status==0){
		           		if(data.list.length>0){
		           			for(var i=0;i<data.list.length;i++){
		           				var newTr = new StringBuffer();
		    					
		    					newTr.append("<tr id='")
		    					     .append(data.list[i].code)
		    					     .append("'>")
		    					         .append("<td>")
		    					         .append(data.list[i].name)
		    					         .append("</td>")
		    					         
		    					         .append("<td>")
		    					         .append(data.list[i].size+data.list[i].unit)
		    					         .append("</td>")
		    					         
		    					         .append("<td>100%</td>")
		    					         
		    					         .append("<td>已上传</td>")
		    					         
		    					         .append("<td>")
		    					         .append(data.list[i].uploadTime)
		    					         .append("</td>")
		    					         
		    					         .append("<td>")
		    					         .append(data.list[i].downloadCount)
		    					         .append("</td>")
		    					         
		    					         .append("<td><button type='button' class='btn btn-primary attachment-delete-link' attachmentId='")
		    					         .append(data.list[i].id)
		    					         .append("' code='")
		    					         .append(data.list[i].code)
		    					         .append("'>删除</button></td>")
		    					     .append("</tr>");    
		    					
		    					$("#attachment-table-tbody").append(newTr.toString());
		           			}
		           		} 
		           		
		           		$("#attachment-modal").modal("show");
		           	 }else{
		           		 alert(data.msg);
		           	 }
	           	 
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 });
	 
	 $(".batch-comment-operation").live("click",function(){
		 var selectedRows = $("input[name='comment-checkbox']:checked");
		 
		 if(selectedRows.length>0){
			 var commentIdArray = new Array(selectedRows.length);
			 var operation = $(this).attr("operation"); 
			 var i = 0;
			 var flag = true;
			 
			 selectedRows.each(function(){
				 commentIdArray[i] = $(this).attr("comment-id");
				 
				 if($(this).attr("comment-status")==operation){
					 flag = false;
					 return false;
				 }
				 
				 i++;
			 });
			 
			 if(flag){
				 commentOperation(commentIdArray,operation);
			 }else{
				 if(operation=='0'){
					 alert("选中的行中已包含'非禁用'评论，不能进行'批量解禁'操作");
				 }else if(operation=='1'){
					 alert("选中的行中已包含'禁用'评论，不能进行'批量禁用'操作"); 
				 }
			 }
		 }else{
			 alert("请先勾选需要进行操作的行");
		 }
	 });
	 
	 
	 $(".batch-hot-operation").live("click",function(){
		 var selectedRows = $("#documentList-table").bootstrapTable('getSelections');
		 var selectedRowsIndex = $("#documentList-table").find(".selected");
		 
		 if(selectedRows.length>0){
			 var rowIndexArray = new Array(selectedRows.length);
			 var documentIdArray = new Array(selectedRows.length);
			 var operation = $(this).attr("operation");
			 var i = 0;
			 var status_flag = true;
			 var hot_flag = true;
			 
			 selectedRowsIndex.each(function(){
				 documentIdArray[i] = selectedRows[i].id;
				 rowIndexArray[i] = $(this).attr("data-index");
				 
				 if(selectedRows[i].status=='1'){
					 status_flag = false;
					 return false;
				 }
				 
				 if(selectedRows[i].isHot==operation){
					 hot_flag = false;
					 return false;
				 }
				 
				 i++;
			 });
			 
			 
			 if(status_flag){
				 if(hot_flag){
					 hotOperation(rowIndexArray,documentIdArray,operation);
				 }else{
					 if(operation=='0'){
						 alert("选中的行中已包含'非热门'文章，不能进行'批量解热'操作");
					 }else if(operation=='1'){
						 alert("选中的行中已包含'热门'文章，不能进行'批量加热'操作"); 
					 }
				 }
			 }else{
				 alert("选中的行中已包含'已禁用'文章，请先对此进行'解禁'操作后再继续后续操作");
			 } 
		 }else{
			 alert("请先勾选需要进行操作的行");
		 }
	 });
	 
	 
	 $(".batch-use-operation").live("click",function(){
		 var selectedRows = $("#documentList-table").bootstrapTable('getSelections');
		 var selectedRowsIndex = $("#documentList-table").find(".selected");
		 
		 if(selectedRows.length>0){
			 var rowIndexArray = new Array(selectedRows.length);
			 var documentIdArray = new Array(selectedRows.length);
			 var operation = $(this).attr("operation");
			 var i = 0;
			 var status_flag = true;
			 
			 selectedRowsIndex.each(function(){
				 documentIdArray[i] = selectedRows[i].id;
				 rowIndexArray[i] = $(this).attr("data-index");
				 
				 if(selectedRows[i].status==operation){
					 status_flag = false;
					 return false;
				 }
				 
				 i++;
			 });
			 
			 
			 if(status_flag){
				 useOperation(rowIndexArray,documentIdArray,operation);
			 }else{
				 if(operation=='0'){
					 alert("选中的行中已包含'非禁用'文章，不能进行'批量解禁'操作");
				 }else if(operation=='1'){
					 alert("选中的行中已包含'已禁用'文章，不能进行'批量禁用'操作"); 
				 }
			 } 
		 }else{
			 alert("请先勾选需要进行操作的行");
		 }
	 });
	 
	 
	 function commentOperation(commentIdArray,operation){
		 $.ajax({
	            type: "POST",
	            url: path+"admin/commentOperation.do",
	            data: {
	           	  "commentIdArray":commentIdArray.toString(),
	           	  "operation":operation
	            },
	            dataType: "json",
	            success: function(data){
	           	 if(data.status==0){
	           		for(var i=0;i<commentIdArray.length;i++){   
                        $("input[comment-id='"+commentIdArray[i]+"']").attr("comment-status",operation);
                        
                        if(operation==1){
                        	$("div[comment-abandoned-id="+commentIdArray[i]+"]").text("已禁用");
                        }else{
                        	$("div[comment-abandoned-id="+commentIdArray[i]+"]").text("");
                        }                                                               
	          		}
	           		
	           		alert("操作成功");
	           		$("input[name='batch-comment-checkbox']").attr("checked",false);
	           		$("input[name='comment-checkbox']").attr("checked",false);
	           	 }else{
	           		 alert(data.msg);
	           	 }
	           	 
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 }
	 
	 
	 function hotOperation(rowIndexArray,documentIdArray,operation){
		  $.ajax({
	            type: "POST",
	            url: path+"admin/hotOperation.do",
	            data: {
	           	  "documentIdArray":documentIdArray.toString(),
	           	  "operation":operation
	            },
	            dataType: "json",
	            success: function(data){
	           	 if(data.status==0){
	           		for(var i=0;i<rowIndexArray.length;i++){   
	         			$("#documentList-table").bootstrapTable('updateRow', {  
	             		    index: rowIndexArray[i],  
	             		    row: {  
	             		    	isHot:operation
	             		    }  
	             		});
	         		}
	           		
	           		alert("操作成功");
	           		$("#documentList-table").bootstrapTable('uncheckAll');
	           	 }else{
	           		 alert(data.msg);
	           	 }
	           	 
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 }
	 
	 
	 function useOperation(rowIndexArray,documentIdArray,operation){
         $.ajax({
	            type: "POST",
	            url: path+"admin/useOperation.do",
	            data: {
	           	  "documentIdArray":documentIdArray.toString(),
	           	  "operation":operation
	            },
	            dataType: "json",
	            success: function(data){
	           	 if(data.status==0){
	           		for(var i=0;i<rowIndexArray.length;i++){   
	          			$("#documentList-table").bootstrapTable('updateRow', {  
	              		    index: rowIndexArray[i],  
	              		    row: {  
	              		    	status:operation
	              		    }  
	              		});
	          		}
	           		
	           		alert("操作成功");
	           		$("#documentList-table").bootstrapTable('uncheckAll');
	           	 }else{
	           		 alert(data.msg);
	           	 }
	           	 
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 }
	 
	 function setSearchParams(){
		 var searchParams = new StringBuffer();
		 
		 var documentTitle = $("input[name='document-title']").val();
		 if($.trim(documentTitle) != ""){
			 searchParams.append("&title=")
			             .append(documentTitle);
		 }
		 
		 var documentTags = $("input[name='document-tags']").val();
		 if($.trim(documentTags) != ""){
			 searchParams.append("&tags=")
			             .append(documentTags);
		 }
		 
		 var createTime = $("input[name='createTime']").val();
		 if($.trim(createTime) != ""){
			 searchParams.append("&createTime=")
			             .append(createTime);
		 }
		 
		 var endTime = $("input[name='endTime']").val();
		 if($.trim(endTime) != ""){
			 searchParams.append("&endTime=")
			             .append(endTime);
		 }
		 
		 var documentStatus = $("input[name='documentStatus']:checked").val();
		 if($.trim(documentStatus) != ""){
			 searchParams.append("&status=")
			             .append(documentStatus);
		 }
		 
		 var documentType = $("input[name='documentType']:checked").val();
		 if($.trim(documentType) != ""){
			 if(documentType=="hot"){
				 searchParams.append("&isHot=")
	                         .append(1);
			 }else if(documentType=="top"){
				 searchParams.append("&isTop=")
                             .append(1);
			 }
		 }
		 
		 return searchParams.toString();
	 }
	 
	 function initTable(){
		 $("#documentList-table").bootstrapTable({
				method: 'get',
		        url: path+"admin/documentList.do",
		        cache: false,
		        striped: true,
		        pagination: true,
		        pageList: [10, 20],
		        pageSize: 10,
		        pageNumber: 1,
		        singleSelect: false,
		        sidePagination: 'server',
		        responseHandler: function responseHandler(pageResult) {
				    if(pageResult.status==0 && pageResult.total>0) {
				    	$("#data-show").css("display","block")
				    	               .next("#empty-prompt").css("display","none");
				        return {
				            "rows": pageResult.rows,
				            "total": pageResult.total
				        };
				    }else {
				    	$("#data-show").css("display","none")
	 	                               .next("#empty-prompt").css("display","block");
				        return {
				            "rows": [],
				            "total": 0
				        };
				    }
				},
		        clickToSelect: true,
		        smartDisplay: true,
		        queryParams:function queryParams(params) {
		        	return {
		        		offset:params.offset,
		            	limit:params.limit
		        	};

		        },
		        rowStyle:rowStyle,
		        columns:[{
		            checkbox:true
		        },{
		            field: 'title',
		            title: '文章标题',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'tags',
		            title: '文章标签',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'lookNum',
		            title: '浏览次数',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'commentNum',
		            title: '评论次数',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'createTime',
		            title: '创建时间',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'createIp',
		            title: '创建IP',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'isHot',
		            title: '是否热门',
		            align: 'center',
		            valign: 'middle',
		            formatter:formatIsHot
		        },{
		            field: 'isTop',
		            title: '是否置顶',
		            align: 'center',
		            valign: 'middle',
		            formatter:formatIsTop
		        },{
		            field: 'status',
		            title: '状态',
		            align: 'center',
		            valign: 'middle',
		            formatter:formatStatus
		        },{
		            title: '操作',
		            align: 'center',
		            valign: 'middle',
		            width:450,
		            formatter:formatOperation
		         }
		        ]
			});
	 }
	 
	 
	 function formatOperation(value, row, index){
		 var operation = new StringBuffer();
		 
		 if(row.status=='0'){
		     operation.append("<a class='btn  btn-primary ' href='")
		              .append(path)
		              .append("admin/editDocument.do?documentId=")
		              .append(row.id)
		              .append("' target='_blank'>编辑</a>&nbsp;&nbsp;")
		              .append("<a class='btn  btn-warning use-link-button ' documentId='") 
			          .append(row.id)
					  .append("' operation='1' rowindex='")
					  .append(index)
					  .append("' href='javascript:void(0)'>禁用</a>&nbsp;&nbsp;");
			 
			 if(row.isHot=='0'){
				 operation.append("<a class='btn  btn-primary hot-link-button ' documentId='") 
				          .append(row.id)
						  .append("' operation='1' rowindex='")
						  .append(index)
						  .append("' href='javascript:void(0)'>加热</a>&nbsp;&nbsp;");
			 }else{
				 operation.append("<a class='btn  btn-info hot-link-button ' documentId='") 
				          .append(row.id)
				          .append("' operation='0' rowindex='")
						  .append(index)
						  .append("' href='javascript:void(0)'>解热</a>&nbsp;&nbsp;");
			 }
			 
			 if(row.isTop=='0'){
				 operation.append("<a class='btn  btn-success top-link-button ' documentId='")
				          .append(row.id)
				          .append("' operation='1' rowindex='")
				          .append(index)
						  .append("' href='javascript:void(0)'>置顶</a>&nbsp;&nbsp;");
			 }else{
				 operation.append("<a class='btn  btn-info top-link-button ' documentId='")
				          .append(row.id)
				          .append("' operation='0' rowindex='")
				          .append(index)
						  .append("' href='javascript:void(0)'>解顶</a>&nbsp;&nbsp;");
			 }
			 
			 operation.append("<a class='btn  btn-success attachment-link-button ' documentId='")
			          .append(row.id)
			          .append("' documentTitle='")
			          .append(row.title)
					  .append("' href='javascript:void(0)'>附件管理</a>&nbsp;&nbsp;");
			 
		     operation.append("<a class='btn  btn-primary comment-link-button ' documentId='")
			          .append(row.id)
			          .append("' documentTitle='")
			          .append(row.title)
			          .append("' operation='1' rowindex='")
			          .append(index)
					  .append("' href='javascript:void(0)'>评论管理</a>");
		 }else{
			 operation.append("<a class='btn  btn-inverse disabled '>编辑</a>&nbsp;&nbsp;")
	                  .append("<a class='btn  btn-success use-link-button ' documentId='") 
				      .append(row.id)
					  .append("' operation='0' rowindex='")
					  .append(index)
					  .append("' href='javascript:void(0)'>解禁</a>&nbsp;&nbsp;");
			 
			 if(row.isHot=='0'){
				 operation.append("<a class='btn  btn-inverse disabled '>加热</a>&nbsp;&nbsp;");
			 }else{
				 operation.append("<a class='btn  btn-inverse disabled '>解热</a>&nbsp;&nbsp;");
			 }
			 
			 if(row.isTop=='0'){
				 operation.append("<a class='btn  btn-inverse disabled '>置顶</a>&nbsp;&nbsp;");
			 }else{
				 operation.append("<a class='btn  btn-inverse disabled '>解顶</a>&nbsp;&nbsp;");
			 }
			 
			 operation.append("<a class='btn  btn-inverse disabled '>附件管理</a>&nbsp;&nbsp;");
			 operation.append("<a class='btn  btn-inverse disabled '>评论管理</a>");
		 }
		 
		 return operation.toString();
	 }
	 
	 function formatIsHot(value, row, index){
	        if(row.isHot=='0'){
	        	return "否";
	        }else if(row.isHot=='1'){
	        	return "是";
	        }else{
	        	return "未知";
	        }
     }
	 
	 function formatIsTop(value, row, index){
	        if(row.isTop=='0'){
	        	return "否";
	        }else if(row.isTop=='1'){
	        	return "是";
	        }else{
	        	return "未知";
	        }
     }
	 
	 function formatStatus(value, row, index){
        if(row.status=='0'){
        	return "正常";
        }else if(row.status=='1'){
        	return "已禁用";
        }else{
        	return "其它状态";
        }
	 }
	 
	function rowStyle(row,index){
		var classes = ['success','error'];

	    if (row.status=='0') {
	        return {
	            classes: 'success'
	        };
	    }

	    if (row.status=='1') {
	        return {
	        	classes: 'error'
	        };
	    }
	    
	    return {};
	}
	
	
	$(".sort-link-a").live('click',function(){
		if(!$(this).hasClass("checked")){
			$("#sort").val($(this).attr("sort"));
			$(".sort-link-a").removeClass("checked");
			$(this).addClass("checked");
			
			forwardPage(1);
		}
	});
	
	//分页事件
	$(".page-button").live('click',function(){
		forwardPage($(this).attr("nowPageNum"));
	});
	$(".pager-select-ul>li").live('click',function(){
		$(this).parent().css("display","none");
		
		var nowPageNum = parseInt($(".pager-select").children(".num").text());
		var linkNum = parseInt($(this).attr("pagevalue"));
		
  		if(nowPageNum!=linkNum){
  			forwardPage(linkNum);
  		}
		
	});
	

    function forwardPage(nowPageNum){
    	$.ajax({
            type: "POST",
            url: path+"admin/commentList.do",
            data: {
           	  "docId":$("#docId").val(),
           	  "nowPageNum":nowPageNum,
           	  "sort":$("#sort").val()
            },
            dataType: "json",
            success: function(data){
            	if(data.status==0){
              		 resetCommentPage(data.result.pageResult,"","");
              	 }else{
              		alert("系统繁忙，请稍候再试");
              	 }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });  
    }
	
	function resetCommentPage(pageResult,docTitle,docId){
		var comments = new StringBuffer();

		if(pageResult.rows!=null && pageResult.rows.length>0){
			comments.append("<div class='comment-operation'>")
			           .append("<input type='checkbox' class='batch-comment-checkbox' name='batch-comment-checkbox'/>")
			           .append("<div class='btn-group operation_bar batch-operation'>")
			              .append("<a class='btn  btn-primary batch-comment-operation' type='button' operation='0'>批量解禁</a>")
			              .append("<a class='btn  btn-warning batch-comment-operation' type='button' operation='1'>批量禁用</a>")
			              .append("<a class='btn  btn-success batch-add-ip-black' type='button' operation='1'>拉入ip黑名单</a>")
			           .append("</div>")
			        .append("</div>");
			
			for(var i=0;i<pageResult.rows.length;i++){
				var floorNum = 0;
				
				if(pageResult.sort=="asc"){
					floorNum = (pageResult.nowPageNum-1)*pageResult.pageSize+i+1;
				}else{
					floorNum = pageResult.total - (pageResult.nowPageNum-1)*pageResult.pageSize - i;
				}
				
				var abandonedShow = "";
				if(pageResult.rows[i].status == 1){
					abandonedShow = "已禁用";
				}
				
				
				comments.append("<div class=\"comment-detail\">")
				             .append("<input type='checkbox' class='comment-checkbox' name='comment-checkbox' comment-status='")
				             .append(pageResult.rows[i].status)
				             .append("' ip='")
				             .append(pageResult.rows[i].ip)
				             .append("' comment-id='")
					         .append(pageResult.rows[i].id)
				             .append("'/><div class=\"comment-detail-title\">")
				                 .append("<span class=\"floor-num\">#")
				                     .append(floorNum)
				                 .append("</span>楼&nbsp;")
				                 .append("<span class=\"comment-user\">")
				                     .append(pageResult.rows[i].commentUser)
				                 .append("</span>于")
				                 .append("<span class=\"comment-date\">")
				                     .append(pageResult.rows[i].commentDate)
				                 .append("</span>发表评论")          
			                     .append("<div class='abandoned' comment-abandoned-id='")
			 		             .append(pageResult.rows[i].id)
					             .append("'>")
					             .append(abandonedShow)
					             .append("</div>")

				             .append("</div>") 
				             .append("<div class=\"comment-detail-content\">")
				                 .append(pageResult.rows[i].commentContent)
				             .append("</div>")
				              
				             .append("<div class=\"agree-against\">")
				                 .append("<span class='comment-agree'><i class='icon-hand-up'></i>赞成<span class='color-red'>(")
				                     .append(pageResult.rows[i].agreeNum)
				                 .append(")</span></span>")
				                 .append("<span class='comment-against'><i class='icon-hand-down'></i>反对<span class='color-red'>(")
				                     .append(pageResult.rows[i].againstNum)
				                 .append(")</span></span>")
				             .append("</div>")
				          .append("</div>");   
			}
			
			
			if(pageResult.allPageNum>1){
				comments.append("<ul class=\"pager\">");
				
				if(pageResult.nowPageNum==1){
					comments.append("<li class=\"previous disabled\"><a href=\"javascript:void(0)\">上一页&larr;</a></li>");
				}else{
					comments.append("<li class=\"previous page-button\" nowPageNum=\"") 
					           .append(pageResult.nowPageNum-1)
					        .append("\"><a href=\"javascript:void(0)\">上一页&larr;</a></li>");
				}
				
				if(pageResult.nowPageNum==pageResult.allPageNum){
					comments.append("<li class=\"next disabled\"><a href=\"javascript:void(0)\">&rarr;下一页</a></li>");
				}else{
					comments.append("<li class=\"next page-button\" nowPageNum=\"")
					           .append(pageResult.nowPageNum+1)
					        .append("\"><a href=\"javascript:void(0)\">&rarr;下一页</a></li>");
				}
				
				comments.append("</ul>")
				        .append("<div class=\"quick-pager-link2\">")
				           .append("<div class=\"quick-pager-show\">") 
				              .append("<span class=\"pager-select\" pagernum=\"")
				                 .append(pageResult.allPageNum)
				              .append("\">第&nbsp;<span class=\"num\">")
				                 .append(pageResult.nowPageNum)
				              .append("</span>&nbsp;页<i class=\"icon-chevron-down\"></i>&nbsp;&nbsp;</span>|&nbsp;&nbsp;共&nbsp;<span class=\"num\">")
				                 .append(pageResult.allPageNum)
				              .append("</span>&nbsp;页")
				           .append("</div>")   
				        .append("</div><br/>");   
			}
		}else{
			comments.append("<div class=\"empty-comment\">暂无评论!</div>");
		}
		
		if(docTitle!=""){
			var commentTitle = new StringBuffer();
		    commentTitle.append("<span class='font-bold'>《")
		                .append(docTitle)
		                .append("》</span>的评论列表(<span class='color-red'>")
		                .append(pageResult.total)
		                .append("</span>)&nbsp;|&nbsp;")
		                .append("<a href='javascript:void(0)' class='checked sort-link-a' sort='default'>默认</a>&nbsp;|&nbsp;")
		                .append("<a href='javascript:void(0)' class='sort-link-a' sort='new'>最新</a>")
		                .append("<input type='hidden' id='sort' value='default'/>")
		                .append("<input type='hidden' id='docId' value='")
		                .append(docId)
		                .append("'/>");
		                
		   $("#comment-list-modal-label").html(commentTitle.toString());
		}
		
		$("#comment-list-modal-body").html(comments.toString());
	}
      
	
	$(".batch-comment-checkbox").live('click',function(){
		$(".comment-checkbox").attr("checked",$(this).prop("checked"));
	});
	
	$(".comment-checkbox").live('click',function(){
		if($(".comment-checkbox").length==$(".comment-checkbox:checked").length && !$(".batch-comment-checkbox").prop("checked")){
			$(".batch-comment-checkbox").attr("checked",true);
		}else if($(".comment-checkbox").length!=$(".comment-checkbox:checked").length && $(".batch-comment-checkbox").prop("checked")){
			$(".batch-comment-checkbox").attr("checked",false);
		}
		
	});
	
	
	$(".modal-show-control").live('show.bs.modal', function () {
		$(this).css("left",($(document).width()-$(this).width())/2);
	});
	
	
	$(".batch-add-ip-black").live('click',function(){
		 var selectedRows = $("input[name='comment-checkbox']:checked");
		 
		 if(selectedRows.length>0){
			 var ipArray = new Array(selectedRows.length);
			 var i = 0;
			 
			 selectedRows.each(function(){
				 ipArray[i] = $(this).attr("ip");
				 
				 i++;
			 });
			 
            
			 $.ajax({
		            type: "POST",
		            url: path+"admin/updateIPInfo.do",
		            data: {
		           	  "ip":ipArray.toString(),
		           	  "operation":1,
		           	  "descrition":"参与非法评论"
		            },
		            dataType: "json",
		            success: function(data){
		           	 if(data.status==0){
		           		alert("操作成功");
		           	 }else{
		           		alert(data.msg);
		           	 }
		           	 
		            },
		            error:function(XMLHttpRequest, textStatus, errorThrown){
		           	     alert("系统繁忙，请稍候再试");
		            }
			 });
		 }else{
			 alert("请先勾选需要进行操作的行");
		 }
	});
});