$(document).ready(function(){
	 initTable();
	 
	 $(".add-tag-modal").click(function(){
		 $("#tags-modal").modal("show");
	 });
	 
	 $("#tags-modal").live('show.bs.modal', function () {
		$(this).find("input[name='tag-code']").val("");
		$(this).find("input[name='tag-level']").attr("checked",false)
		                                       .parent().removeClass("on");
		$(this).find("input[name='tag-oneLevelCode']").val("");
	 });
	 
	 
	 $("#tag-search-button").click(function(){
		 console.log(setSearchParams());
		 $("#tags-table").bootstrapTable('refresh',{url:path+"admin/tags.do?T=1"+setSearchParams()});
	 });
	 
	 
	 $("#add-tag-submit").click(function(){
		 var tagCodeInput = $("#add-tag-form").find("input[name='tag-code']");
		 var tagLevelInput = $("#add-tag-form").find("input[name='tag-level']");
		 var tagOneLevelCode = $("#add-tag-form").find("input[name='tag-oneLevelCode']").val();
		 
		 if(!checkElement(tagCodeInput,false,0)){
			 return;
		 }
		 
		 if(!checkElement(tagLevelInput,false,0)){
			 return;
		 }
		 
		 $.ajax({
	            type: "POST",
	            url: path+"admin/addTag.do",
	            data: {
	           	  "tagCode":tagCodeInput.val(),
	           	  "tagLevel":$("#add-tag-form").find("input[name='tag-level']:checked").val(),
	           	  "tagOneLevelCode":tagOneLevelCode
	            },
	            dataType: "json",
	            success: function(data){
	               if(data.status==0){
	            	   alert("添加成功");
	            	   window.location = path+"admin/tags.jsp";
	               }else{
	            	   alert("添加失败");
	               }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		 });
	 });
	 
	 
	 $("#tags-delete").click(function(){
		 var selectedRows = $("#tags-table").bootstrapTable('getSelections');
		 var selectedRowsIndex = $("#tags-table").find(".selected");
		 
		 if(selectedRows.length>0){
			 var tagIdArray = new Array(selectedRows.length);
	
			 var i = 0;
			 var status_flag = true;
			 
			 selectedRowsIndex.each(function(){
				 tagIdArray[i] = selectedRows[i].id;
				 
				 i++;
			 });
			 
			 $.ajax({
		            type: "POST",
		            url: path+"admin/deleteTag.do",
		            data: {
		           	  "tagIdArray":tagIdArray.toString()
		            },
		            dataType: "json",
		            success: function(data){
		            	alert(data.msg);
		           	    window.location = path+"admin/tags.jsp";
		            },
		            error:function(XMLHttpRequest, textStatus, errorThrown){
		           	     alert("系统繁忙，请稍候再试");
		            }
			 });
			 
		 }else{
			 alert("请先勾选需要进行操作的行");
		 }
	 });
	 
	 
	 
	 function initTable(){
		 $("#tags-table").bootstrapTable({
				method: 'get',
		        url: path+"admin/tags.do",
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
		            field: 'code',
		            title: '标签名称',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'level',
		            title: '标签等级',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'oneLevelCode',
		            title: '所属一级标签',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'createTime',
		            title: '创建时间',
		            align: 'center',
		            valign: 'middle'
		        }
		      ]
			});
	 }
	 
	 
	 function setSearchParams(){
		 var searchParams = new StringBuffer();
		 
		 var tagCode = $("#tag-search-form").find("input[name='tag-code']").val();
		 if($.trim(tagCode) != ""){
			 searchParams.append("&code=")
			             .append(tagCode);
		 }
		 
		 var tagOneLevelCode = $("#tag-search-form").find("input[name='tag-oneLevelCode']").val();
		 if($.trim(tagOneLevelCode) != ""){
			 searchParams.append("&oneLevelCode=")
			             .append(tagOneLevelCode);
		 }
		 
		 var tagOneLevelCode = $("#tag-search-form").find("input[name='tag-oneLevelCode']").val();
		 if($.trim(tagOneLevelCode) != ""){
			 searchParams.append("&oneLevelCode=")
			             .append(tagOneLevelCode);
		 }
		 
		 var beginTime = $("#tag-search-form").find("input[name='createTime']").val();
		 if($.trim(beginTime) != ""){
			 searchParams.append("&beginTime=")
			             .append(beginTime);
		 }
		 
		 var endTime = $("#tag-search-form").find("input[name='endTime']").val();
		 if($.trim(endTime) != ""){
			 searchParams.append("&endTime=")
			             .append(endTime);
		 }
		 
		 var tagLevel = $("#tag-search-form").find("input[name='tag-level']:checked").val();
		 if($.trim(tagLevel) != ""){
			 searchParams.append("&level=")
                         .append(tagLevel);
		 }
		 
		 
		 
		 return searchParams.toString();
	 }
	 
	 function rowStyle(row,index){
			var classes = ['success','info'];

		    if (row.level=='1') {
		        return {
		            classes: 'success'
		        };
		    }

		    if (row.level=='2') {
		        return {
		        	classes: 'info'
		        };
		    }
		    
		    return {};
	}
});