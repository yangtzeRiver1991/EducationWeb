$(document).ready(function(){
	$(".bootstrap-switch").each(function(){
		var state;
		if($(this).attr("switch")=="true"){
			state = true;
		}else{
			state = false;
		}
	
		var fun = $(this).attr("fun");
		$(this).bootstrapSwitch({
			  state:state,
		  	  handleWidth:$(this).attr("handleWidth"),
		  	  labelWidth:$(this).attr("labelWidth"),
		  	  size:$(this).attr("switch-size"),
		  	  onText:$(this).attr("onText"),
		  	  offText:$(this).attr("offText"),
		  	  onColor:$(this).attr("onColor"),
		  	  offColor:$(this).attr("offColor"),
		  	  onSwitchChange: function(event, stateResult) {
		  		  reloadSystemConfig(fun,stateResult);
		      }
		 });
	});	
	
	
	$(".input-config").blur(function(){
		reloadSystemConfig($(this).attr("fun"),$(this).val());
	});
	
	function reloadSystemConfig(fun,stateResult){
		$.ajax({
            type: "POST",
            url: path+"admin/systemConfig.do",
            data: {
           	  "fun":fun,
           	  "state":stateResult
            },
            dataType: "json",
            success: function(data){
           	 if(data.status==0){
           		 window.location = path + "admin/systemConfig.jsp";
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