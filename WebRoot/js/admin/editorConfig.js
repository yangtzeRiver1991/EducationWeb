var editor = new wangEditor('editor');
$(document).ready(function(){
	editor.config.uploadImgUrl = path+'admin/uploadImg.do';
	editor.config.emotions = emotionsJson;
    editor.create();
});


