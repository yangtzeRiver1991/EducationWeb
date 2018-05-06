$(document).ready(function(){
	var carousel_uplpad_files = new Array();
	
	var carousel_uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'select-carousel',
		url : path+'admin/uploadCarousel.do',
		flash_swf_url : path+'js/admin/plupload/Moxie.swf',
		silverlight_xap_url : path+'js/admin/plupload/Moxie.xap',
		file_data_name:'carousel',
		
		filters : {
			max_file_size : '1mb',
			prevent_duplicates : true,
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"}
			]
		},

		init: {
			PostInit: function() {
				$("#upload-carousel-prompt").css("display","none");

				$("#upload-carousel").click(function() {
					carousel_uploader.start();
					return false;
				});
			},

			FilesAdded: function(up, files) {
				if(carousel_uplpad_files.length>0 || files.length>1 ){
					$("#upload-carousel-prompt").css("display","block").html("<span class='color-red'>每个文章的轮播图片只能存在一个!</span>");
					plupload.each(files, function(file) {
						carousel_uploader.removeFile(file);
					});
					
					return false;
				}
				carousel_uplpad_files = carousel_uplpad_files.concat(files);
				$("#upload-carousel-prompt").css("display","block").html("<span class='color-black'>图片已选择,待上传</span>");
			},

			UploadProgress: function(up, file) {
				$("#upload-carousel-prompt").css("display","block").html("<span class='color-black'>上传中,已完成:"+file.percent + "%</span>");
			},
			
			FileUploaded:function(up, file,resObj) {
				var result = eval("("+resObj.response+")");
				
				if(result.status==0){
					$("#upload-carousel-prompt").css("display","block").html("<img src='"+result.msg+"' style='height:300px'/>");
					$("#documentList-table").bootstrapTable('updateRow', {  
              		    index: $("#carousel-rowIndex").val(),  
              		    row: {  
              		    	isTop:1
              		    }  
	              	});

	           		$("#documentList-table").bootstrapTable('uncheckAll');
				}else{
					$("#upload-carousel-prompt").css("display","block").html("<span class='color-red'>上传失败</span>");
				}
			},
			
			UploadComplete:function(up, files) {	
				$("#select-carousel").attr("disabled",false);
				$("#remove-carousel-upload-queue").attr("disabled",false);
			},

			Error: function(up, err) {
				$("#upload-carousel-prompt").css("display","block").html("<span class='color-red'>\nError #" + err.code + ": " + err.message);
			}
		}
	});

	carousel_uploader.init();
	
	$("#carousel-modal").live('show.bs.modal', function () {
		var multipart_params = {
			documentId:$("#carousel-docId").val()	
		};

		carousel_uploader.setOption("multipart_params",multipart_params);
    });
	
	$("#carousel-modal").live('hide.bs.modal', function () {
		$("#upload-carousel-prompt").html("");
		
		plupload.each(carousel_uplpad_files, function(file) {
			carousel_uploader.removeFile(file);
		});
		
		carousel_uplpad_files = [];
    });
	
	
	$("#upload-carousel").click(function(){
		$("#select-carousel").attr("disabled",true);
		$("#remove-carousel-upload-queue").attr("disabled",true);
	});
	
	$("#remove-carousel-upload-queue").click(function(){
		$("#select-carousel").attr("disabled",true);
		$("#upload-carousel").attr("disabled",true);
		
		plupload.each(carousel_uplpad_files, function(file) {
			if(file.status != plupload.DONE){
				carousel_uploader.removeFile(file);
			}
		});
		
		$("#select-carousel").attr("disabled",false);
		$("#upload-carousel").attr("disabled",false);
		
	});
});