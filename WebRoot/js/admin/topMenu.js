$(document).ready(function(){
	resetCurrentHref();
	
	function resetCurrentHref(){
		var currentHref = $("#currentHref").val();
		
		$(".top-menu>li").removeClass("active").parent().find("#"+currentHref).addClass("active");
	}
});