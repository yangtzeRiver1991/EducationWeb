wangEditor.config.printLog = false;
var editor = new wangEditor('editor');
$(document).ready(function(){
	editor.config.menus = [
	                       'emotion'
	                    ];
	
    editor.config.emotions = emotionsJson;
    
    editor.config.emotionsShow = 'value';
    
    editor.onchange = function () {
        var imgsSize = 0;
        var text = editor.$txt.text();
        

        if(/\]/.test(text)){
        	imgsSize = text.match(/\]/g).length;
        }
        
        if(imgsSize>=imgSizeLimit){
        	editor.disableMenusExcept('emotions');
        }else{
        	editor.enableMenusExcept('emotions');
        }
        
        
        var imgSizeLimitPrompt = "",commentSizeLimitPrompt = "";
        if(imgSizeLimit-imgsSize>0){
        	imgSizeLimitPrompt = "剩余"+(imgSizeLimit-imgsSize)+"个表情";
        }else{
        	imgSizeLimitPrompt = "已超出"+(imgsSize-imgSizeLimit)+"个表情";
        }
        
    	if(commentSizeLimit-$.trim(text).length>0){
    		commentSizeLimitPrompt = ",剩余"+(commentSizeLimit-$.trim(text).length)+"个字符";
    	}else{
    		commentSizeLimitPrompt = ",已超出"+($.trim(text).length-commentSizeLimit)+"个字符";
    	}
        
        $(".comment-dialog-prompt").text(imgSizeLimitPrompt+commentSizeLimitPrompt);
    };
    
    editor.create();
    
    if(commentSwitch!="on"){
    	editor.$txt.html("<p class='text-align-center color-gray'>"+blog_index_comment_switch_off+"</p>");
    	editor.disable();
    }
});