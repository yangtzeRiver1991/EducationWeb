$(document).ready(function(){
	var needLogin = $("#needLogin").val();
    if(needLogin=="yes"){
    	$("#user-login-modal").modal("show");
    }
	
	$("#userAdd").click(function(){
		$("#user-add-modal").modal("show");
	});
	
	$("#userLogin").click(function(){
		$("#user-login-modal").modal("show");
	});
	
	$("#user-login-submit").click(function(){
		var username = $("#username-login").val();
		if(! /^[a-zA-Z0-9_]{2,10}$/.test(username)){
			alert("用户名由2~10位的数字、字母以及下划线组成");
			return;
		}
		
		var password = $("#password-login").val();
		if(! /^[a-zA-Z0-9_]{6,16}$$/.test(password)){
			alert("密码由6~16位以内的数字、字母以及下划线组成");
			return;
		}
		
		$.ajax({
            type: "POST",
            url: path+"user/login.do",
            data: {
           	 "username":username,
           	 "password":password
            },
            dataType: "json",
            success: function(data){ 	 
	           	 if(data.status==0){
	           		 alert(data.msg);
	           		 window.location = path; 
	           	 }else{
	           		alert(data.msg);
	           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
	});
	
	$("#user-add-submit").click(function(){
		var username = $("#username-add").val();
		if(! /^[a-zA-Z0-9_]{2,10}$/.test(username)){
			alert("用户名由2~10位的数字、字母以及下划线组成");
			return;
		}
		
		var mobile = $("#mobile-add").val();
		if(! /^1[3|4|5|7|8][0-9]{9}$/.test(mobile)){
			alert("请输入正确的手机号");
			return;
		}
		
		var password = $("#password-add").val();
		if(! /^[a-zA-Z0-9_]{6,16}$$/.test(password)){
			alert("密码由6~16位以内的数字、字母以及下划线组成");
			return;
		}
		
		var passwordAgain = $("#passwordAgain-add").val();
		if(passwordAgain!=password){
			alert("两次密码输入须一致");
			return;
		}
		
		$.ajax({
            type: "POST",
            url: path+"user/register.do",
            data: {
           	 "username":username,
           	 "password":password,
           	 "mobile":mobile
            },
            dataType: "json",
            success: function(data){ 	 
	           	 if(data.status==0){
	           		 alert(data.msg);
	           		 window.location = path; 
	           	 }else{
	           		alert(data.msg);
	           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert("系统繁忙，请稍候再试");
            }
	    });
	});
	
	
	resetCurrentHref();
	
	function resetCurrentHref(){
		var currentHref = $("#currentHref").val();
		
		$(".top-menu>li").removeClass("active").parent().find("#"+currentHref).addClass("active");
	}
	
	
	$("#me-weixin-link").click(function(){
		$("#me-weixin-modal-body").html("<img src='"+path+"img/me-weixin.png' class='me-weixin-img'/>");
		$("#me-weixin-modal").modal("show");
	});
	
	$("#sys-setup-link").click(function(){
		$("#sys-setup-verificationCode").val("")
		                                .parent().append("<img src=\""+path+"getVerificationCode.do?time="+new Date().getTime()+"\" id=\"sys-setup-verificationCode-img\" class=\"verificationCodeImg\" style=\"top:-8px;\"/>");
		$("#sys-setup-modal").modal("show");
	});
	
	$("#sys-setup-modal").live('hide.bs.modal', function () {
		  $("#sys-setup-verificationCode-img").remove();
    });
	
	$("#sys-setup-submit").click(function(){
		var originalLanguageConfig = $("#original-language-config").val();
		var nowLanguageConfig = $("input[name='language-config']:checked").val();
		var verificationCode = $("#sys-setup-verificationCode").val();
		
		if($.trim(verificationCode)==""){
        	alert(blog_index_validate_code_input_empty_prompt);
        	return false;
        }
        var regExp = new RegExp("^[a-zA-Z0-9]{4}$");        
        if(!regExp.test(verificationCode)){
        	alert(blog_index_validate_code_input_illegal_verification_code_prompt);
        	return false;
        }
        
        if(originalLanguageConfig==nowLanguageConfig){
        	alert(blog_index_language_no_switch_prompt);
        	return false;
        }
        
		
        $.ajax({
            type: "POST",
            url: path+"switchLanguage.do",
            data: {
           	  "languageConfig":nowLanguageConfig,
           	  "verificationCode":verificationCode
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		$("#sys-setup-modal").modal("hide");
           		window.location = path;
           	 }else{
           		 alert(data.msg);
           		 $("#sys-setup-verificationCode").val("")
                                                 .next().attr("src",path+"getVerificationCode.do?time="+new Date().getTime());
           	 }
           	 
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
           	     alert(blog_common_system_busy_prompt);
           	     $("#sys-setup-verificationCode").val("")
                                                 .next().attr("src",path+"getVerificationCode.do?time="+new Date().getTime());
            }
	    });
	});
	
	
	$(".hot-tag-link").click(function(){
		$(".search-input").val("");
		$("#page-form").children("input[name='childTag']").val($(this).attr("data-tag"))
		               .parent().children("input[name='currentHref']").val($(this).attr("data-parent-tag"))
                       .parent().submit();
	});
	
	
	$(".hot-doc-link").click(function(){
		if($(".left-main").length>0){
			window.location = path+"detail.do?documentId="+$(this).attr("documentId");
		}else{
			window.location = path+"detail.do?documentId="+$(this).attr("documentId");
		}
	});
});