$(document).ready(function(){
	$("#courseSign").click(function(){
		var courseId = $(this).attr("data-course-id");
		var courseName = $(this).attr("data-course-name");
		
		$.ajax({
            type: "POST",
            url: path+"user/courseSign.do",
            data: {
           	  "courseId":courseId,
           	  "courseName":courseName
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		 alert(data.msg);
           		 window.location = path + "user/myCourse.do";
           	 }else{
           		 alert(data.msg);
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
            }
	    }); 
	});
	
	imgReponsoeAuto();
	
	$(".comment-button-submit").click(function(){
		if(commentSwitch!="on"){
			alert(blog_index_comment_switch_off);
			return false;
		}
		
        var comment = $.trim(editor.$txt.text());
        
        if($.trim(comment)==""){
        	alert(blog_index_comment_input_empty_promot);
        	return false;
        }
        
        var imgsSize = 0;
        if(/\]/.test(comment)){
        	imgsSize = comment.match(/\]/g).length;
        }
        if(imgsSize>imgSizeLimit){
        	alert(blog_index_comment_input_too_many_emotions);
        	return false;
        }
        
        if(comment.length>commentSizeLimit){
        	alert(blog_index_comment_input_too_many_comment_content);
        	return false;
        }
        
        var verificationCode = $("#comment-verificationCode").val();
        if($.trim(verificationCode)==""){
        	alert(blog_index_validate_code_input_empty_prompt);
        	return false;
        }
        var regExp = new RegExp("^[a-zA-Z0-9]{4}$");        
        if(!regExp.test(verificationCode)){
        	alert(blog_index_validate_code_input_illegal_verification_code_prompt);
        	return false;
        }
        
        
        $.ajax({
            type: "POST",
            url: path+"comment.do",
            data: {
           	  "comment":comment,
           	  "verificationCode":verificationCode,
           	  "docId":$("#docId").val()
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		 
           		 alert(data.msg);

           		 $("#comment-verificationCode").val("")
                                               .next().attr("src",path+"getVerificationCode.do?time="+new Date().getTime())
                                               .parent().prev().text("");
           		 
           		 editor.clear();
           		 
           		 $("#sort").val("new");
    			 $(".sort-link-a").removeClass("checked");
    			 $("a[sort='new']").addClass("checked");
           		 
           		 resetCommentPage(data.result.pageResult);
           		 scrollOffset($(".comment-list"),true,70);
           	 }else{
           		 alert(data.msg);
           		 $("#comment-verificationCode").val("")
                                               .next().attr("src",path+"getVerificationCode.do?time="+new Date().getTime());
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
           	     $("#comment-verificationCode").val("")
                                               .next().attr("src",path+"getVerificationCode.do?time="+new Date().getTime());
            }
	    });  
	});
	
	$(".sort-link-a").click(function(){
		var commentNum = $(".comment-num").text().trim();
		
		if(commentNum>0){
			if(!$(this).hasClass("checked")){
				$("#sort").val($(this).attr("sort"));
				$(".sort-link-a").removeClass("checked");
				$(this).addClass("checked");
				
				forwardPage(1);
			}
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
            url: path+"commentList.do",
            data: {
           	  "docId":$("#docId").val(),
           	  "nowPageNum":nowPageNum,
           	  "sort":$("#sort").val()
            },
            dataType: "json",
            success: function(data){
            	if(data.status==0){
              		 resetCommentPage(data.result.pageResult);
              		 scrollOffset($(".comment-list"),true,70);
              	 }else{
              		alert(blog_common_system_busy_prompt);
              	 }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
            }
	    });  
    }
	
	function resetCommentPage(pageResult){
		var comments = new StringBuffer();

		if(pageResult.rows!=null && pageResult.rows.length>0){
			for(var i=0;i<pageResult.rows.length;i++){
				var floorNum = 0;
				
				if(pageResult.sort=="asc"){
					floorNum = (pageResult.nowPageNum-1)*pageResult.pageSize+i+1;
				}else{
					floorNum = pageResult.total - (pageResult.nowPageNum-1)*pageResult.pageSize - i;
				}
				
				
				comments.append("<div class=\"comment-detail\">")
				             .append("<div class=\"comment-detail-title\">")
				                 .append("<span class=\"floor-num\">#")
				                     .append(floorNum)
				                 .append("</span>")
				                 .append(blog_index_comment_floor)
				                 .append("&nbsp;<span class=\"comment-user\">")
				                     .append(pageResult.rows[i].commentUser)
				                 .append("</span>&nbsp;")
				                 .append(blog_index_comment_at)
				                 .append("&nbsp;<span class=\"comment-date\">")
				                     .append(pageResult.rows[i].commentDate)
				                 .append("</span>&nbsp;")
				                 .append(blog_index_comment_lower)
				              .append("</div>");
				
			   	if(pageResult.rows[i].status==0){
			   		  comments.append("<div class=\"comment-detail-content\">")
	                             .append(pageResult.rows[i].commentContent)
	                          .append("</div>")
	                          
	                          .append("<div class=\"comment-other-operation\">")
	                             .append("<span class=\"comment-agree\" commentId=\"") 
	                                .append(pageResult.rows[i].id)
	                             .append("\"><i class=\"icon-hand-up\"></i>")
	                                .append(blog_index_comment_agree)
			   		                .append("<span class=\"color-red show-num\" show-num=\"")
			   		                   .append(pageResult.rows[i].agreeNum)
			   		                .append("\">");
			   		  
	                     if(pageResult.rows[i].agreeNum>0){
	                        comments.append("(")
	                                   .append(pageResult.rows[i].agreeNum)
	                                .append(")");   
	                     }       
			   		        comments.append("</span>")
	                             .append("</span>")
	                             .append("<span class=\"comment-against\" commentId=\"") 
	                                .append(pageResult.rows[i].id)
	                             .append("\"><i class=\"icon-hand-down\"></i>")
	                                .append(blog_index_comment_against)
			   		                .append("<span class=\"color-red show-num\" show-num=\"")
			   		                   .append(pageResult.rows[i].againstNum)
			   		                .append("\">");
			   		        
                         if(pageResult.rows[i].againstNum>0){
	                        comments.append("(")
	                                   .append(pageResult.rows[i].againstNum)
	                                .append(")");   
	                     }
                            comments.append("</span>")
                                 .append("</span>")
	                          .append("</div>")
	                     .append("</div>");
			   	}else{
			   		  comments.append("<div class=\"comment-disabled-content\">")
	                              .append(blog_index_comment_disabled_prompt)
	                          .append("</div>")
	                     .append("</div>");
			   	}              
	   
			}
			
			
			if(pageResult.allPageNum>1){
				comments.append("<ul class=\"pager\">");
				
				if(pageResult.nowPageNum==1){
					comments.append("<li class=\"previous disabled\"><a href=\"javascript:void(0)\">")
					        .append(blog_index_pager_previous)
					        .append("&larr;</a></li>");
				}else{
					comments.append("<li class=\"previous page-button\" nowPageNum=\"") 
					           .append(pageResult.nowPageNum-1)
					           .append("\"><a href=\"javascript:void(0)\">")
					           .append(blog_index_pager_previous)
					           .append("&larr;</a></li>");
				}
				
				if(pageResult.nowPageNum==pageResult.allPageNum){
					comments.append("<li class=\"next disabled\"><a href=\"javascript:void(0)\">&rarr;")
					        .append(blog_index_pager_next)
					        .append("</a></li>");
				}else{
					comments.append("<li class=\"next page-button\" nowPageNum=\"")
					        .append(pageResult.nowPageNum+1)
					        .append("\"><a href=\"javascript:void(0)\">&rarr;")
					        .append(blog_index_pager_next)
					        .append("</a></li>");
				}
				
				comments.append("</ul>")
				        .append("<div class=\"quick-pager-link2\">")
				           .append("<div class=\"quick-pager-show\">") 
				              .append("<span class=\"pager-select\" pagernum=\"")
				                 .append(pageResult.allPageNum)
				              .append("\">")
					          .append(blog_index_pager_no)
					          .append("&nbsp;<span class=\"num\">")
				                 .append(pageResult.nowPageNum)
				              .append("</span>&nbsp;")
					          .append(blog_index_pager_page)
					          .append("<i class=\"icon-chevron-down\"></i>&nbsp;&nbsp;</span>|&nbsp;&nbsp;")
					          .append(blog_index_pager_all)
					          .append("&nbsp;<span class=\"num\">")
				                 .append(pageResult.allPageNum)
				              .append("</span>&nbsp;")
					          .append(blog_index_pager_all_page)
				           .append("</div>")   
				        .append("</div>");   
			}
		}else{
			comments.append("<div class=\"empty-comment\">")
					.append(blog_index_comment_empty)
					.append("!</div>");
		}
		
		$(".comment-list").html(comments.toString());
		$(".comment-num").text(pageResult.total);
	}
	
	
	//文件下载
	$(".attachment-detail-download").click(function(){
		if(attachmentDownloadSwitch != "on"){
			alert(blog_index_attachment_download_switch_off);
			return false;
		}
		
		var downloadLimit = $(this).attr("class");
		
		if(downloadLimit=="attachment-detail-download-disabled"){
			return false;
		}
		
		var code = $(this).attr("code");
		
		$.ajax({
            type: "POST",
            url: path+"prepareAttachmentDownload.do",
            data: {
           	  "code":code
            },
            dataType: "json",
            success: function(data){
            	if(data.status==0){
              		 $("#attachment-download-form").children("input[name='code']").val(code)
              		                               .parent().submit();
              	 }else{
              		if(data.obj != null && data.obj != ""){
              			var downloadIntervals = data.obj;
              			if(downloadIntervals>0){
              				$(".attachment-detail-download").removeClass("attachment-detail-download").addClass("attachment-detail-download-disabled");
              				var downloadCountdown = setInterval(function () {
              					downloadIntervals--;
                  				$(".attachment-detail-download-prompt").text(downloadIntervals + " " + blog_index_file_download_countdown);
                  				
                  				if(downloadIntervals<1){
                  					clearInterval(downloadCountdown);
                  					$(".attachment-detail-download-disabled").removeClass("attachment-detail-download-disabled").addClass("attachment-detail-download")
                  					                                .next().text("");
                  				}
                			},1000);
              			}
              			
              		} 
              	 }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
            }
	    });
	});
	
	
	//评论赞成
	$(".comment-agree").live('click',function(){
		var commentId = $(this).attr("commentId");
		var showNumDiv = $(this).children(".show-num");
		
		if($.trim(commentId) == ""){
			return false;
		}
		
		$.ajax({
            type: "POST",
            url: path+"commentAgree.do",
            data: {
           	  "commentId":commentId
            },
            dataType: "json",
            success: function(data){
            	 if(data.status==0){
            		var showNum = parseInt(showNumDiv.attr("show-num"));

            		showNumDiv.text("("+(showNum+1)+")");
            		showNumDiv.attr("show-num",showNum+1);
              	 }else{
              		alert(data.msg);
              	 }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
            }
	    });
	});
	
	//评论举报
	$(".comment-against").live('click',function(){
		var commentId = $(this).attr("commentId");
		var showNumDiv = $(this).children(".show-num");
		
		if($.trim(commentId) == ""){
			return false;
		}
		
		$.ajax({
            type: "POST",
            url: path+"commentAgainst.do",
            data: {
           	  "commentId":commentId
            },
            dataType: "json",
            success: function(data){
            	 if(data.status==0){
            		var showNum = parseInt(showNumDiv.attr("show-num"));

            		showNumDiv.text("("+(showNum+1)+")");
            		showNumDiv.attr("show-num",showNum+1);
              	 }else{
              		alert(data.msg);
              	 }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
            }
	    });
	});
});