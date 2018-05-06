$(document).ready(function(){
	function tagShow(){
		$.ajax({
            type: "POST",
            url: path+"admin/tags.do",
            dataType: "json",
            success: function(pageResult){
               if(pageResult.status==0){
            	   var tags = pageResult.rows;
            	   var tagsDiv = new StringBuffer();
                   
            	   tagsDiv.append("<ul class=\"tag-level1\">");
            	   for(var i=0;i<tags.length;i++){
            		   if(tags[i].level==1){
            			   tagsDiv.append("<li>")
            			              .append("<input type=\"checkbox\" value=\"")
            			                  .append(tags[i].code)
            			              .append("\" name=\"tag\"/>")
            			                  .append(tags[i].code)
            			              .append("<ul class=\"tag-level2\">"); 
            			   
            			   for(var j=0;j<tags.length;j++){ 
            				   if(tags[j].oneLevelCode==tags[i].code){
            					   tagsDiv.append("<li>")
            					              .append("<input type=\"checkbox\" value=\"")
            					                  .append(tags[j].code)
            					              .append("\" name=\"tag\" parent-link=\"")
            					                  .append(tags[i].code)
            					              .append("\"/>")
            					                  .append(tags[j].code)
            					          .append("</li>");
            				   }
            			   }      
            			              
            			       tagsDiv.append("</ul>")
            			          .append("</li>");
            		   }
            	   }
            	   tagsDiv.append("</ul>");
            	   
            	   $("#tags-show").after(tagsDiv.toString());
               }else{
            	   alert("标签列表加载失败");
               }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
		$("#tags-show").append();
	}
	
	tagShow();
	
	//checkbox级联
	$("input[parent-link]:checkbox").live("click",function(){
		var parentLinkNum = $("input[parent-link='"+$(this).attr("parent-link")+"']:checked").length;
		var parentCheck = $("input[value='"+$(this).attr("parent-link")+"']:checkbox").is(':checked');

		if(parentLinkNum>0 && !parentCheck){
			$("input[value='"+$(this).attr("parent-link")+"']:checkbox").attr("checked",true);
		}
		else if(parentLinkNum==0 && parentCheck){
			$("input[value='"+$(this).attr("parent-link")+"']:checkbox").attr("checked",false);
		}
	});
	
	
	$("#doc-add-button").click(function(){
		var title = $("#title");
		if(!checkElement(title,true,70)){
			return false;
		}
		
		
		var tagsCheck = $("input[name='tag']:checked");
		if(tagsCheck.length<1){
			alert("请选择所属标签");
		}
		var tags = new StringBuffer();
		tagsCheck.each(function(index,element){
			tags.append($(this).val());
			/*if(index != tagsCheck.length-1){
				tags.append(",");
			}*/
			tags.append(",");
		});
		
		var intro = $("#intro");
		if(!checkElement(intro,true,70)){
			return false;
		}
		
		
		var content = editor.$txt.html();
		if(content==""){
			alert("请填写文章内容");
		}
		
		var imgs = editor.$txt.find('img');
		var uploadImgs = new StringBuffer();
		
		imgs.each(function(index,element){
			var imgSrc = $(this).attr("src");
			
			if(imgSrc.indexOf("/upload/")!=-1){
				uploadImgs.append(imgSrc)
				          .append(",");
			}
		});
		
		$.ajax({
            type: "POST",
            url: path+"admin/addDocument.do",
            data: {
           	  "title":title.val(),
           	  "tags":tags.toString(),
           	  "intro":intro.val(),
           	  "content":content,
           	  "uploadImgs":uploadImgs.toString()
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		window.location = path+"admin/documentList.jsp"; 
           	 }else{
           		 alert(data.msg);
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
	});
});