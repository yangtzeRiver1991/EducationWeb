$(document).ready(function(){
	initTable();
	
	$(".new-ip-black").click(function(){
		$("#ip-manage-form")[0].reset();
		$("#new-ip-manage-input-error-prompt").text("");
		$("#ip-manage-modal").modal("show");
	});
	
	$("#new-ip-manage-submit").click(function(){
		var ip = $("#ip-manage-modal").find("input[name='ip']").val();
		if($.trim(ip)==""){
			$("#ip-manage-modal").find("input[name='ip']").focus();
			$("#new-ip-manage-input-error-prompt").text("ip不能为空");
			return false;
		}
		
		var descrition = $("#ip-manage-modal").find("textarea[name='descrition']").val();
		if($.trim(descrition)==""){
			$("#ip-manage-modal").find("textarea[name='descrition']").focus();
			$("#new-ip-manage-input-error-prompt").text("描述不能为空");
			return false;
		}
		
		$("#new-ip-manage-input-error-prompt").text("");
		
		$.ajax({
            type: "POST",
            url: path+"admin/addIPBlack.do",
            data: {
           	  "ip":ip,
           	  "descrition":descrition
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		window.location = path+"admin/ipManage.jsp"; 
           	 }else{
           		$("#new-ip-manage-input-error-prompt").text(data.msg);
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
	});
	
	
	$(".ip-operation-button").live('click',function(){
		var ip = $(this).attr("ip");
		var operation = $(this).attr("operation");
		var rowIndexArray = new Array($(this).attr("rowindex"));
		
		$("#ip-descrition-submit").attr({"ip":ip,"operation":operation,"rowIndexArray":rowIndexArray});
		
		$("#ip-input-descrition-modal").find(".ip-operation-descrition").val("")
                                       .parent().parent().parent().parent().parent().modal("show");

	});
	
	$("#ip-descrition-submit").click(function(){
		var ip = $(this).attr("ip");
		var operation = $(this).attr("operation");
		var descrition = $("#ip-input-descrition-modal").find(".ip-operation-descrition").val();
		var rowIndexArrayStr = $(this).attr("rowIndexArray");

		if($.trim(descrition)==""){
			$("#ip-input-descrition-modal").find("textarea[name='descrition']").focus();
			$("#ip-descrition-input-error-prompt").text("描述不能为空");
			return false;
		}
		
		$("#ip-descrition-input-error-prompt").text("");
		
		$.ajax({
            type: "POST",
            url: path+"admin/updateIPInfo.do",
            data: {
           	  "ip":ip,
           	  "operation":operation,
           	  "descrition":descrition
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		rowIndexArray = rowIndexArrayStr.split(",");
           		
           		for(var i=0;i<rowIndexArray.length;i++){   
          			$("#ip-info-list-table").bootstrapTable('updateRow', {  
              		    index: rowIndexArray[i],  
              		    row: {  
              		    	status:operation
              		    }  
              		});
          		}
           		
           		$("#ip-info-list-table").bootstrapTable('uncheckAll');
           		$("#ip-input-descrition-modal").modal("hide");
           		
           		alert("操作成功");
           	 }else{
           		$("#ip-descrition-input-error-prompt").text(data.msg);
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
	});
	
	$(".ip-descrition-look-button").live('click',function(){
		var ip = $(this).attr("ip");
		var descrition = $(this).attr("descrition");
		
		$("#ip-descrition-modal").find(".modal-title").text(ip+"的操作日志记录")
		                         .parent().next().html(descrition)
		                         .parent().parent().parent().modal("show");
	});
	
	$("#ip-search-button").click(function(){
		$("#ip-info-list-table").bootstrapTable('refresh',{url:path+"admin/iPInfoList.do?T=1"+setSearchParams()});
	});
	
	$(".batch-ip-operation").click(function(){
		 var selectedRows = $("#ip-info-list-table").bootstrapTable('getSelections');
		 var selectedRowsIndex = $("#ip-info-list-table").find(".selected");
		 
		 if(selectedRows.length>0){
			 var rowIndexArray = new Array(selectedRows.length);
			 var ipArray = new Array(selectedRows.length);
			 var operation = $(this).attr("operation");
			 var i = 0;
			 var status_flag = true;
			 
			 selectedRowsIndex.each(function(){
				 ipArray[i] = selectedRows[i].ip;
				 rowIndexArray[i] = $(this).attr("data-index");

				 if(selectedRows[i].status==operation){
					 status_flag = false;
					 return false;
				 }
				 
				 i++;
			 });
			 
			 if(status_flag){
					
				 $("#ip-descrition-submit").attr({"ip":ipArray,"operation":operation,"rowindexArray":rowIndexArray});
				 $("#ip-input-descrition-modal").find(".ip-operation-descrition").val("")
			                                    .parent().parent().parent().parent().parent().modal("show");
			 }else{
				 if(operation=='0'){
					 alert("选中的行中已包含'正常'ip，不能进行'批量恢复正常'操作");
				 }else if(operation=='1'){
					 alert("选中的行中已包含'黑名单'ip，不能进行'批量拉黑名单'操作"); 
				 }
			 }
		 }else{
			 alert("请先勾选需要进行操作的行");
		 }
	});
	
	function setSearchParams(){
		 var searchParams = new StringBuffer();
		 
		 var ip = $("#ip").val();
		 if($.trim(ip) != ""){
			 searchParams.append("&ip=")
			             .append(ip);
		 }

		 var ipStatus = $("input[name='ipStatus']:checked").val();
		 if($.trim(ipStatus) != ""){
			 searchParams.append("&status=")
			             .append(ipStatus);
		 }
		 
		 return searchParams.toString();
	}
	
	function initTable(){
		 $("#ip-info-list-table").bootstrapTable({
				method: 'get',
		        url: path+"admin/iPInfoList.do",
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
		        clickToSelect: false,
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
		            field: 'ip',
		            title: 'IP',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'createDate',
		            title: '创建时间',
		            align: 'center',
		            valign: 'middle'
		        },{
		        	field: 'ipSite',
		            title: '归属地',
		            align: 'center',
		            valign: 'middle'
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
		            formatter:formatOperation
		         }
		        ]
			});
	 }
	
	function formatStatus(value, row, index){
		if(value==0){
        	return "正常";
        }else{
        	return "黑名单";
        }
	}
	
	function formatOperation(value, row, index){
		var operation = new StringBuffer();
		
		if(row.status==0){
			operation.append("<a class='btn  btn-success ip-operation-button' operation='1' rowindex='")
			            .append(index)
			            .append("' ip='")
                        .append(row.ip)
                     .append("'>拉黑名单</a>&nbsp;&nbsp;")
		}else{
			operation.append("<a class='btn  btn-info ip-operation-button' operation='0' rowindex='")
			            .append(index)
			            .append("' ip='")
                        .append(row.ip)
                     .append("'>恢复正常</a>&nbsp;&nbsp;")
		}
		
		    operation.append("<a class='btn  btn-primary ip-descrition-look-button' ip='")
                        .append(row.ip)
                        .append("' descrition='")
                        .append(row.descrition)
                     .append("'>日志浏览</a>&nbsp;&nbsp;")
		
		return operation.toString();
	}
	
	function rowStyle(row,index){
		var classes = ['success','info'];

	    if(index%2==0) {
	        return {
	            classes: 'success'
	        };
	    }else{
	    	return {
		        classes: 'info'
		    };
	    }
	}

});