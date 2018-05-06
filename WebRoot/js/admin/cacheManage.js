$(document).ready(function(){
	initTable();
	
	$(".new-cache-manage").click(function(){
		$("#cache-manage-form")[0].reset();
		$("#cache-manage-form").find(".pay_list_c1").removeClass("on");
		$("#new-cache-manage-input-error-prompt").text("");
		$("#cache-manage-modal").modal("show");
	});
	
	$("#new-cache-manage-submit").click(function(){
		var code = $("#cache-manage-modal").find("input[name='code']").val();
		if($.trim(code)==""){
			$("#cache-manage-modal").find("input[name='code']").focus();
			$("#new-cache-manage-input-error-prompt").text("缓存code不能为空");
			return false;
		}
		
		var name = $("#cache-manage-modal").find("input[name='name']").val();
		if($.trim(name)==""){
			$("#cache-manage-modal").find("input[name='name']").focus();
			$("#new-cache-manage-input-error-prompt").text("缓存名称不能为空");
			return false;
		}
		
		var allEntries = $("#cache-manage-modal").find("input[name='allEntries']:checked").val();
		if($.trim(allEntries)==""){
			$("#new-cache-manage-input-error-prompt").text("请选择key策越");
			return false;
		}
		
		var descrition = $("#cache-manage-modal").find("textarea[name='descrition']").val();
		if($.trim(descrition)==""){
			$("#cache-manage-modal").find("textarea[name='descrition']").focus();
			$("#new-cache-manage-input-error-prompt").text("描述不能为空");
			return false;
		}
		
		$("#new-cache-manage-input-error-prompt").text("");
		
		$.ajax({
            type: "POST",
            url: path+"admin/addCacheManage.do",
            data: {
           	  "code":code,
           	  "name":name,
           	  "allEntries":allEntries,
           	  "descrition":descrition
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		window.location = path+"admin/cacheManage.jsp"; 
           	 }else{
           		$("#new-cache-manage-input-error-prompt").text(data.msg);
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
	});
	
	$(".scan-cache-button").live('click',function(){
		$("#cache-manage-update-modal").find(".pay_list_c1").removeClass("on");
		
		var cacheId = $(this).attr("cache-id");
		var code = $(this).attr("code");
		var name = $(this).attr("name");
		var allEntries = $(this).attr("allEntries");
		var descrition = $(this).attr("descrition");
		
		$("#cache-manage-update-modal").find("input[name='code']").val(code);
		$("#cache-manage-update-modal").find("input[name='name']").val(name);
		$("#cache-manage-update-modal").find("textarea[name='descrition']").val(descrition);
		
		$("#cache-manage-update-modal").find("input[name='allEntries'][value="+allEntries+"]").attr("checked",true)
		                                                                                      .parent().addClass("on");
		$("#cache-manage-update-submit").attr("cache-id",cacheId);
		$("#cache-manage-update-submit").attr("cache-code",code);
		$("#cache-manage-update-submit").attr("cache-name",name);
		$("#cache-manage-update-submit").attr("cache-allEntries",allEntries);
		$("#cache-manage-update-submit").attr("cache-descrition",descrition);
		
		$("#cache-manage-update-modal").modal("show");
	});
	
	$("#cache-manage-update-submit").click(function(){
		var code = $("#cache-manage-update-modal").find("input[name='code']").val();
		if($.trim(code)==""){
			$("#cache-manage-update-modal").find("input[name='code']").focus();
			$("#cache-manage-update-input-error-prompt").text("缓存code不能为空");
			return false;
		}
		
		var name = $("#cache-manage-update-modal").find("input[name='name']").val();
		if($.trim(name)==""){
			$("#cache-manage-update-modal").find("input[name='name']").focus();
			$("#cache-manage-update-input-error-prompt").text("缓存名称不能为空");
			return false;
		}
		
		var allEntries = $("#cache-manage-update-modal").find("input[name='allEntries']:checked").val();
		if($.trim(allEntries)==""){
			$("#cache-manage-update-input-error-prompt").text("请选择key策越");
			return false;
		}
		
		var descrition = $("#cache-manage-update-modal").find("textarea[name='descrition']").val();
		if($.trim(descrition)==""){
			$("#cache-manage-update-modal").find("textarea[name='descrition']").focus();
			$("#cache-manage-update-input-error-prompt").text("描述不能为空");
			return false;
		}
		
		$("#cache-manage-update-input-error-prompt").text("");
		
		var finallCacheCode = "";
		var finallCacheName = "";
		var finallCacheAllEntries = "";
		var finallCacheDescrition = "";
		
		if(code != $(this).attr("cache-code")){
			finallCacheCode = code;
		}
		if(name != $(this).attr("cache-name")){
			finallCacheName = name;
		}
		if(allEntries != $(this).attr("cache-allEntries")){
			finallCacheAllEntries = allEntries;
		}
		if(descrition != $(this).attr("cache-descrition")){
			finallCacheDescrition = descrition;
		}
		
		if(finallCacheCode!="" || finallCacheName!="" || finallCacheAllEntries!="" || finallCacheDescrition!=""){
			var cacheId = $(this).attr("cache-id");
			
			$.ajax({
	            type: "POST",
	            url: path+"admin/updateCacheManage.do",
	            data: {
	           	  "id":cacheId,
	           	  "code":finallCacheCode,
	           	  "name":finallCacheName,
	           	  "allEntries":finallCacheAllEntries,
	           	  "descrition":descrition
	            },
	            dataType: "json",
	            success: function(data){
	           	 if(data.status==0){
	           		alert("设置成功");
	           		window.location = path+"admin/cacheManage.jsp"; 
	           	 }else{
	           		$("#new-cache-manage-input-error-prompt").text(data.msg);
	           	 }
	           	 
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	           	     alert("系统繁忙，请稍候再试");
	            }
		    });
		}else{
			alert("设置成功");
			$("#cache-manage-update-modal").modal("hide");
		}
	});
	
	$(".refresh-cache-button").live('click',function(){
		var code = $(this).attr("code");
		
		if($.trim(code)==""){
			return false;
		}
		
		var allEntries = $(this).attr("allEntries");
		
		if(allEntries=="true"){
			$("input[name='cache-key']").val("");
			$("#cache-key-input-submit").attr("cache-code",code);
			$("#cache-key-input-modal").modal("show");
		}else{
			$.ajax({
	            type: "POST",
	            url: path+"admin/refreshCache.do",
	            data: {
	           	  "cahceCode":code
	            },
	            dataType: "json",
	            success: function(data){
	           	 if(data.status==0){
	           		alert("刷新成功"); 
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
	
	$("#cache-key-input-submit").click(function(){
		var cacheCode = $(this).attr("cache-code");
		var cacheKey = $("input[name='cache-key']").val();

		if($.trim(cacheKey)==""){
			$("#cache-key-input-error-prompt").text("cache-key不能为空");
			return false;
		}
		
		$("#cache-key-input-error-prompt").text("");
		
		$.ajax({
            type: "POST",
            url: path+"admin/refreshCache.do",
            data: {
           	  "cahceCode":cacheCode,
           	  "cacheKey":cacheKey
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		alert("刷新成功"); 
           		$("#cache-key-input-modal").modal("hide");
           	 }else{
           		alert(data.msg); 
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	    alert("系统繁忙，请稍候再试");
            }
	    });
	});
	
	function initTable(){
		 $("#cacheManageList-table").bootstrapTable({
				method: 'get',
		        url: path+"admin/cacheManageList.do",
		        cache: false,
		        striped: true,
		        pagination: false,
		        singleSelect: false,
		        sidePagination: 'server',
		        responseHandler: function responseHandler(obj) {
				    if(obj.status==0 && obj.list.length>0) {
				    	$("#data-show").css("display","block")
				    	               .next("#empty-prompt").css("display","none");
				        return {
				            "rows": obj.list,
				            "total": obj.list.length
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
		        rowStyle:rowStyle,
		        columns:[
		        {
		            field: 'code',
		            title: '缓存code',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'name',
		            title: '缓存名称',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'allEntries',
		            title: 'key策越',
		            align: 'center',
		            valign: 'middle',
		            formatter:formatAllEntries
		        },{
		            field: 'createDate',
		            title: '创建时间',
		            align: 'center',
		            valign: 'middle'
		        },{
		            field: 'updateDate',
		            title: '更新时间',
		            align: 'center',
		            valign: 'middle'
		        },{
		            title: '操作',
		            align: 'center',
		            valign: 'middle',
		            formatter:formatOperation
		         }
		        ]
			});
	 }
	
	function formatAllEntries(value, row, index){
		if(row.allEntries){
        	return "启用";
        }else{
        	return "关闭";
        }
	}
	
	function formatOperation(value, row, index){
		var operation = new StringBuffer();
		
		operation.append("<a class='btn  btn-success refresh-cache-button' code='")
		            .append(row.code)
		         .append("' allEntries='")
		            .append(row.allEntries)
		         .append("'>刷新缓存</a>&nbsp;&nbsp;")
		         
		         .append("<a class='btn  btn-info scan-cache-button' cache-id='")
		            .append(row.id)
		         .append("' code='")
		            .append(row.code)
		         .append("' name='")
		            .append(row.name)
		         .append("' allEntries='")
		            .append(row.allEntries)
		         .append("' descrition='")
		            .append(row.descrition)
		         .append("'>查看</a>");
		
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