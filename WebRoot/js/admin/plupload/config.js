$(document).ready(function(){
	var uplpad_files = new Array();
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'select-file',
		url : path+'admin/uploadAttachment.do',
		flash_swf_url : path+'js/admin/plupload/Moxie.swf',
		silverlight_xap_url : path+'js/admin/plupload/Moxie.xap',
		file_data_name:'attachment',
		
		filters : {
			max_file_size : '200mb',
			prevent_duplicates : true,
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Wps files", extensions : "doc,xls"},
				{title : "Zip files", extensions : "zip"},
				{title : "Rar files", extensions : "rar"},
				{title : "gz files", extensions : "gz"},
				{title : "jar files", extensions : "jar"}
			]
		},

		init: {
			PostInit: function() {
				$("#upload-prompt").css("display","none");

				$("#upload-file").click(function() {
					uploader.start();
					return false;
				});
			},

			FilesAdded: function(up, files) {
				uplpad_files = uplpad_files.concat(files);
	
				$("#upload-prompt").css("display","none");
				
				plupload.each(files, function(file) {
					var newTr = new StringBuffer();
					
					newTr.append("<tr id='")
					     .append(file.id)
					     .append("'>")
					         .append("<td name='attachment-name'>")
					         .append(file.name)
					         .append("</td>")
					         
					         .append("<td name='attachment-size'>")
					         .append(plupload.formatSize(file.size))
					         .append("</td>")
					         
					         .append("<td name='upload-percent'>0</td>")
					         .append("<td name='status'>待上传</td>")
					         .append("<td name='upload-time'></td>")
					         .append("<td name='download-count'></td>")
					         .append("<td name='operation'></td>")
					     .append("</tr>");    
					                  
					$("#attachment-table-tbody").append(newTr.toString());
				});
			},

			UploadProgress: function(up, file) {
				$("#"+file.id).children("td[name='upload-percent']").text(file.percent + "%")
				              .next().text("上传中");
			},
			
			FileUploaded:function(up, file,resObj) {
				var result = eval("("+resObj.response+")");
				
				if(result.status==0){
					var attachment_delete_link = new StringBuffer();
					
				    attachment_delete_link.append("<button type='button' class='btn btn-primary attachment-delete-link' attachmentId='")
								          .append(result.obj.id)
								          .append("' code='")
								          .append(result.obj.code)
								          .append("'>删除</button>");
					
					$("#"+file.id).children("td[name='status']").text("已上传")
		                          .next().text(getNowFormatDate("yyyy-MM-dd HH:mm:ss"))
		                          .next().text(0)
		                          .next().html(attachment_delete_link.toString());
				}else{
					$("#"+file.id).children("td[name='upload-percent']").text("0")
					              .next().text("上传失败");
				}
			},
			
			UploadComplete:function(up, files) {	
				$("#select-file").attr("disabled",false);
				$("#remove-upload-queue").attr("disabled",false);
			},

			Error: function(up, err) {
				$("#upload-prompt").text("\nError #" + err.code + ": " + err.message)
				                   .css("display","block");
			}
		}
	});

	uploader.init();
	
	$("#attachment-modal").live('show.bs.modal', function () {
		var multipart_params = {
			documentId:$("#attachment-docId").val()	
		};

		uploader.setOption("multipart_params",multipart_params);
    });
	
	$("#attachment-modal").live('hide.bs.modal', function () {
		$("#attachment-table-tbody").html("")
		                            .parent().next().text("");
		
		plupload.each(uplpad_files, function(file) {
			uploader.removeFile(file);
		});
		
		uplpad_files = [];
    });
	
	
	$("#upload-file").click(function(){
		$("#select-file").attr("disabled",true);
		$("#remove-upload-queue").attr("disabled",true);
	});
	
	$("#remove-upload-queue").click(function(){
		$("#select-file").attr("disabled",true);
		$("#upload-file").attr("disabled",true);
		
		plupload.each(uplpad_files, function(file) {
			if(file.status != plupload.DONE){
				uploader.removeFile(file);
				$("#"+file.id).remove();
			}
		});
		
		$("#select-file").attr("disabled",false);
		$("#upload-file").attr("disabled",false);
		
	});
});