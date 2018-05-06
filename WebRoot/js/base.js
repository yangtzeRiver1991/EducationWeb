$(document).ajaxStart(function () {  
  $.blockUI({
	  message:"<span style='background-color: #fff;border:3px solid #aaa;height:30px;font-size:20px;padding:6px 10px;'><img src='"+path+"img/loading.gif'/>"+blog_common_loading_prompt+"<span>"
  });  
});  
$(document).ajaxStop(function () {  
      $.unblockUI();  
});


/**
* jquery 元素定位
* @param element(目标定位元素)
*/
function scrollOffset(element,scrollDistance) {
    $("body,html").animate({
      scrollTop: element.offset().top - scrollDistance
    }, 0);
}

/**
 * a标签去除outline虚线,防止影响美观
 */
$("a").live("focus",function(){
	$(this).blur();
}); 

/**
 * css3属性支持判断
 * @param prop(元素css属性)
 */
function support_css3(prop){
	  var div = document.createElement('div'),
          vendors = 'Ms O Moz Webkit'.split(' '),
          len = vendors.length;
	  
	  if ( prop in div.style) return true;
	
	  prop = prop.replace(/^[a-z]/, function(val) {
	     return val.toUpperCase();
	  });
	
	  while(len--) {
	     if ( vendors[len] + prop in div.style ) {
	        return true;
	     } 
	  }
	  
	  return false;
}

/**
 * js StringBuffer
 */
function StringBuffer() { 
	  this.buffer = []; 
} 
StringBuffer.prototype.append = function append(string) { 
      this.buffer.push(string); 
      return this; 
}; 
StringBuffer.prototype.toString = function toString() { 
      return this.buffer.join(""); 
}; 

/**
 * 获取当前日期
 * @returns
 */
function getNowFormatDate(dateType) {
	if(dateType=="yyyy-MM"){
		var date = new Date();
	    var month = date.getMonth() + 1;
	    
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    
	    var currentdate = new StringBuffer();
	    currentdate.append(date.getFullYear())
	               .append("-")
	               .append(month);
	    
	    return currentdate.toString();
	}
	
	if(dateType=="yyyy-MM-dd"){
		var date = new Date();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
	    
	    var currentdate = new StringBuffer();
	    currentdate.append(date.getFullYear())
	               .append("-")
	               .append(month)
	               .append("-")
                   .append(strDate);
	    
	    return currentdate.toString();
	}
	
	if(dateType=="yyyy-MM-dd HH:mm:ss"){
		var date = new Date();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var hour24 = date.getHours();
	    var minute = date.getMinutes();
	    var seconds = date.getSeconds();
	    
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
	    
	    var currentdate = new StringBuffer();
	    currentdate.append(date.getFullYear())
	               .append("-")
	               .append(month)
	               .append("-")
                   .append(strDate)
	               .append(" ")
	               .append(hour24)
	               .append(":")
	               .append(minute)
	               .append(":")
	               .append(seconds)
	    
	    return currentdate.toString();
	}
	
    return "";
}  

/**
 * jquery 元素输入验证:1为空判断 2正则表达式判断 3时间验证 4ajax回调函数
 * @param element(目标验证元素)
 * @param isScroll(是否需要滚动:true/false)
 * @param scrollDistance(滚动距离)
 */
function checkElement(element,isScroll,scrollDistance){
	  element.tooltip('destroy');
	  
	  if(typeof(element.attr("related-elementId"))!="undefined"){
		  var relatedVal = $("#"+element.attr("related-elementId")).val();

		  if(relatedVal != element.val()){
			  if(isScroll){
				  scrollOffset(element.eq(0),scrollDistance); 
			  }
			  element.eq(0).attr("title",element.attr("related-prompt"));
			  element.eq(0).tooltip("show");
			  element.eq(0).focus();
			  
			  return false;
		  }
		  element.tooltip('destroy');
		  return true;
	  }
	  
	  
	  //判断是否需要为空判断
	  if(typeof(element.attr("non-empty"))!="undefined"){
		  var nonEmptyPrompt = element.attr("non-empty-prompt");
		  
		  //radio为空提示方式
		  if(element.attr("type") == "radio"){
			  var check_size = 0;
			  element.each(function(){
				  if($(this).prop('checked')){
					  check_size++;
				  }
			  });
			  
			  if(check_size==0){
				  if(isScroll){
					  scrollOffset(element.eq(0),scrollDistance); 
				  } 
				  element.eq(0).attr("title",nonEmptyPrompt);
				  element.eq(0).tooltip("show");
				  element.eq(0).focus();
				  
				  return false;
			  }
		  }
		  
		  //select为空提示方式
		  else if(element[0].tagName =="select"){
			  if($.trim(element.val())==""){
				  if(isScroll){
					  scrollOffset(element,scrollDistance); 
				  }
				  element.attr("title",nonEmptyPrompt);
				  element.tooltip("show");
				  element.focus();
				  
				  return false;
			  }
		  }
		  else{
			  if($.trim(element.val())==""){
				  if(isScroll){
					  scrollOffset(element,scrollDistance); 
				  }
				  element.attr("title",nonEmptyPrompt);
				  element.tooltip("show");
				  element.focus();
				  
				  return false;
			  }
		  }
		  
	  }
	
	 
	  //判断是否需要正则表达式判断
	  if(typeof(element.attr("mypattern"))!="undefined" && $.trim(element.val())!=""){
		  var regExp = new RegExp(element.attr("mypattern"));
		  if(!regExp.test(element.val())){
			  if(isScroll){
				  scrollOffset(element,scrollDistance); 
			  }
			  
			  element.attr("title",element.attr("pattern-prompt"));
			  element.tooltip("show");
			  element.focus();
			  
			  return false;
		  }
	  }
	  
	  //时间验证:大于当前时间
	  if(typeof(element.attr("nowTimeLimitGT"))!="undefined"){
		  var nowDate = getNowFormatDate();
		  
		  if(element.val()<nowDate){
			  element.attr("title","需大于当前日期:"+nowDate);
			  
			  element.tooltip("show");
			  element.focus();
			  
			  return false;
		  }
	  }
	  
	  //时间验证:小于当前时间
	  if(typeof(element.attr("nowTimeLimitLT"))!="undefined"){
		  var nowDate = getNowFormatDate();
		  
		  if(element.val()>=nowDate){
			  element.attr("title","需小于当前日期:"+nowDate);
			  
			  element.tooltip("show");
			  element.focus();
			  
			  return false;
		  }
	  }
	  
	  //时间验证:大于开始时间
	  if(typeof(element.attr("beginTimeLimitGT"))!="undefined"){
		  var beginTime = element.parent().prev().find("input[name='beginTime']").val();
		  
		  if(element.val()<=beginTime){
			  element.attr("title","应大于开始日期"+beginTime);
			  
			  element.tooltip("show");
			  element.focus();
			  
			  return false;
		  }
	  }
	  
	  
	  //判断是否需要ajax回调函数判断
	  if(typeof(element.attr("ajaxfunc"))!="undefined"){
		  return eval(element.attr("ajaxfunc"));
	  }else{
		  element.tooltip('destroy');
		  return true;
	  }
	  
}


var path;
var emotionsJson;
var commentSwitch;
$(document).ready(function(){
	  path = $("#path").val();
	
	 /**
	   * 自定义checkbox
	   */
	  $("input[type='checkbox']").click(function(){
		  $(this).parent().toggleClass("on_check");
	  }); 
	  $("input[type='checkbox']").each(function(){
		  var checked = $(this).prop("checked");
		  
		  if(checked){
			  $(this).parent().addClass("on_check");
		  }
		  else{
			  $(this).parent().removeClass("on_check");
		  }
	  });
	  
	  
	  /**
	   * 自定义radio
	   */
	  $("input[type='radio']").click(function(){
		  var radioArray =  $("input[type='radio'][name='"+$(this).attr("name")+"']");

		  radioArray.each(function(){
			  $(this).parent().removeClass("on");
		  });
		  $(this).parent().toggleClass("on");
	  });
	  
	  $("input[type='radio']").each(function(){
		  var checked = $(this).prop("checked");
		  
		  if(checked){
			  $(this).parent().addClass("on");
		  }
		  else{
			  $(this).parent().removeClass("on");
		  }
	  });
	
	
	/**
	 * 失去焦点元素验证
	 */
	$(".blurcheck").live('blur',function(e){
  	   checkElement($(this),true,70);
    });
	
	$("body").not(".pager-select").click(function(){
		$("#pager-select").css("display","none");
	});

	/**
	 * 自定义select
	 */
	$(".pager-select").live('click',function(){
		var pagerSelect = $("#pager-select");
		if(pagerSelect.length>0){
			var pagerSelectDispaly = pagerSelect.css("display");
            var pagernum = parseInt($(this).attr("pagernum"));
			var eleTop = $(this).offset().top - 3;
            if(pagernum>5){
            	eleTop -= 100;
            }else{
            	 eleTop = eleTop - pagernum*25;
            }
			pagerSelect.css("top",eleTop);
			
			if(pagerSelectDispaly=="none"){
				pagerSelect.css("display","block");
			}else{
				pagerSelect.css("display","none");
			}
			
		}else{
			var eleLeft = $(this).offset().left;
			var eleWidth = $(this).parent().width() - 8;
			var pagernum = parseInt($(this).attr("pagernum"));
			
			var eleTop = $(this).offset().top - 3;
            if(pagernum>5){
            	eleTop -= 100;
            }else{
            	 eleTop = eleTop - pagernum*25;
            }		
			
			var div = new StringBuffer();
			    div.append("<ul id='pager-select' class='pager-select-ul' style='position:absolute;top:")
			       .append(eleTop)
			       .append("px;left:")
			       .append(eleLeft)
			       .append("px;width:")
			       .append(eleWidth)
			       .append("px;max-height:100px;'>");
			    
		   for(var i=1;i<=pagernum;i++){
			   div.append("<li pagevalue='" )
			      .append(i)
			      .append("'>")
			      .append(blog_index_pager_no)
			      .append("&nbsp;")
			      .append(i)
			      .append("&nbsp;")
			      .append(blog_index_pager_page);
		   }	    
			    
		       div.append("</ul>");
			
			$("body").append(div.toString());
		}
	});
	
	
	/**
	 * 验证码点击刷新
	 */
	 $(".verificationCodeImg").live("click",function(){
 		 $(this).attr("src",path+"getVerificationCode.do?time="+new Date().getTime());
 	 });
	 
	 
	 emotionsJson = {
				'qq': {
		            title: 'qq表情', 
		            data: [
							{
								'icon':path+'img/emotions/qq/1.gif',
								'value':'[qq_1]'
							},
							{
								'icon':path+'img/emotions/qq/2.gif',
								'value':'[qq_2]'
							},
							{
								'icon':path+'img/emotions/qq/3.gif',
								'value':'[qq_3]'
							},
							{
								'icon':path+'img/emotions/qq/4.gif',
								'value':'[qq_4]'
							},
							{
								'icon':path+'img/emotions/qq/5.gif',
								'value':'[qq_5]'
							},
							{
								'icon':path+'img/emotions/qq/6.gif',
								'value':'[qq_6]'
							},
							{
								'icon':path+'img/emotions/qq/7.gif',
								'value':'[qq_7]'
							},
							{
								'icon':path+'img/emotions/qq/8.gif',
								'value':'[qq_8]'
							},
							{
								'icon':path+'img/emotions/qq/9.gif',
								'value':'[qq_9]'
							},
							{
								'icon':path+'img/emotions/qq/10.gif',
								'value':'[qq_10]'
							},
							{
								'icon':path+'img/emotions/qq/11.gif',
								'value':'[qq_11]'
							},
							{
								'icon':path+'img/emotions/qq/12.gif',
								'value':'[qq_12]'
							},
							{
								'icon':path+'img/emotions/qq/13.gif',
								'value':'[qq_13]'
							},
							{
								'icon':path+'img/emotions/qq/14.gif',
								'value':'[qq_14]'
							},
							{
								'icon':path+'img/emotions/qq/15.gif',
								'value':'[qq_15]'
							},
							{
								'icon':path+'img/emotions/qq/16.gif',
								'value':'[qq_16]'
							},
							{
								'icon':path+'img/emotions/qq/17.gif',
								'value':'[qq_17]'
							},
							{
								'icon':path+'img/emotions/qq/18.gif',
								'value':'[qq_18]'
							},
							{
								'icon':path+'img/emotions/qq/19.gif',
								'value':'[qq_19]'
							},
							{
								'icon':path+'img/emotions/qq/20.gif',
								'value':'[qq_20]'
							},
							{
								'icon':path+'img/emotions/qq/21.gif',
								'value':'[qq_21]'
							},
							{
								'icon':path+'img/emotions/qq/22.gif',
								'value':'[qq_22]'
							},
							{
								'icon':path+'img/emotions/qq/23.gif',
								'value':'[qq_23]'
							},
							{
								'icon':path+'img/emotions/qq/24.gif',
								'value':'[qq_24]'
							},
							{
								'icon':path+'img/emotions/qq/25.gif',
								'value':'[qq_25]'
							},
							{
								'icon':path+'img/emotions/qq/26.gif',
								'value':'[qq_26]'
							},
							{
								'icon':path+'img/emotions/qq/27.gif',
								'value':'[qq_27]'
							},
							{
								'icon':path+'img/emotions/qq/28.gif',
								'value':'[qq_28]'
							},
							{
								'icon':path+'img/emotions/qq/29.gif',
								'value':'[qq_29]'
							},
							{
								'icon':path+'img/emotions/qq/30.gif',
								'value':'[qq_30]'
							},
							{
								'icon':path+'img/emotions/qq/31.gif',
								'value':'[qq_31]'
							},
							{
								'icon':path+'img/emotions/qq/32.gif',
								'value':'[qq_32]'
							},
							{
								'icon':path+'img/emotions/qq/33.gif',
								'value':'[qq_33]'
							},
							{
								'icon':path+'img/emotions/qq/34.gif',
								'value':'[qq_34]'
							},
							{
								'icon':path+'img/emotions/qq/35.gif',
								'value':'[qq_35]'
							},
							{
								'icon':path+'img/emotions/qq/36.gif',
								'value':'[qq_36]'
							},
							{
								'icon':path+'img/emotions/qq/37.gif',
								'value':'[qq_37]'
							},
							{
								'icon':path+'img/emotions/qq/38.gif',
								'value':'[qq_38]'
							},
							{
								'icon':path+'img/emotions/qq/39.gif',
								'value':'[qq_39]'
							},
							{
								'icon':path+'img/emotions/qq/40.gif',
								'value':'[qq_40]'
							},
							{
								'icon':path+'img/emotions/qq/41.gif',
								'value':'[qq_41]'
							},
							{
								'icon':path+'img/emotions/qq/42.gif',
								'value':'[qq_42]'
							},
							{
								'icon':path+'img/emotions/qq/43.gif',
								'value':'[qq_43]'
							},
							{
								'icon':path+'img/emotions/qq/44.gif',
								'value':'[qq_44]'
							},
							{
								'icon':path+'img/emotions/qq/45.gif',
								'value':'[qq_45]'
							},
							{
								'icon':path+'img/emotions/qq/46.gif',
								'value':'[qq_46]'
							},
							{
								'icon':path+'img/emotions/qq/47.gif',
								'value':'[qq_47]'
							},
							{
								'icon':path+'img/emotions/qq/48.gif',
								'value':'[qq_48]'
							},
							{
								'icon':path+'img/emotions/qq/49.gif',
								'value':'[qq_49]'
							},
							{
								'icon':path+'img/emotions/qq/50.gif',
								'value':'[qq_50]'
							}
		            ]
		        },
		        'tusiji': {
		            title: '兔斯基', 
		            data: [
							{
								'icon':path+'img/emotions/tusiji/1.gif',
								'value':'[tusiji_1]'
							},
							{
								'icon':path+'img/emotions/tusiji/2.gif',
								'value':'[tusiji_2]'
							},
							{
								'icon':path+'img/emotions/tusiji/3.gif',
								'value':'[tusiji_3]'
							},
							{
								'icon':path+'img/emotions/tusiji/4.gif',
								'value':'[tusiji_4]'
							},
							{
								'icon':path+'img/emotions/tusiji/5.gif',
								'value':'[tusiji_5]'
							},
							{
								'icon':path+'img/emotions/tusiji/6.gif',
								'value':'[tusiji_6]'
							},
							{
								'icon':path+'img/emotions/tusiji/7.gif',
								'value':'[tusiji_7]'
							},
							{
								'icon':path+'img/emotions/tusiji/8.gif',
								'value':'[tusiji_8]'
							},
							{
								'icon':path+'img/emotions/tusiji/9.gif',
								'value':'[tusiji_9]'
							},
							{
								'icon':path+'img/emotions/tusiji/10.gif',
								'value':'[tusiji_10]'
							},
							{
								'icon':path+'img/emotions/tusiji/11.gif',
								'value':'[tusiji_11]'
							},
							{
								'icon':path+'img/emotions/tusiji/12.gif',
								'value':'[tusiji_12]'
							},
							{
								'icon':path+'img/emotions/tusiji/13.gif',
								'value':'[tusiji_13]'
							},
							{
								'icon':path+'img/emotions/tusiji/14.gif',
								'value':'[tusiji_14]'
							},
							{
								'icon':path+'img/emotions/tusiji/15.gif',
								'value':'[tusiji_15]'
							},
							{
								'icon':path+'img/emotions/tusiji/16.gif',
								'value':'[tusiji_16]'
							},
							{
								'icon':path+'img/emotions/tusiji/17.gif',
								'value':'[tusiji_17]'
							},
							{
								'icon':path+'img/emotions/tusiji/18.gif',
								'value':'[tusiji_18]'
							},
							{
								'icon':path+'img/emotions/tusiji/19.gif',
								'value':'[tusiji_19]'
							},
							{
								'icon':path+'img/emotions/tusiji/20.gif',
								'value':'[tusiji_20]'
							},
							{
								'icon':path+'img/emotions/tusiji/21.gif',
								'value':'[tusiji_21]'
							},
							{
								'icon':path+'img/emotions/tusiji/22.gif',
								'value':'[tusiji_22]'
							},
							{
								'icon':path+'img/emotions/tusiji/23.gif',
								'value':'[tusiji_23]'
							},
							{
								'icon':path+'img/emotions/tusiji/24.gif',
								'value':'[tusiji_24]'
							},
							{
								'icon':path+'img/emotions/tusiji/25.gif',
								'value':'[tusiji_25]'
							},
							{
								'icon':path+'img/emotions/tusiji/26.gif',
								'value':'[tusiji_26]'
							},
							{
								'icon':path+'img/emotions/tusiji/27.gif',
								'value':'[tusiji_27]'
							},
							{
								'icon':path+'img/emotions/tusiji/28.gif',
								'value':'[tusiji_28]'
							}
		            ]
		        },
		        'xiee1': {
		            title: '邪恶1', 
		            data: [
							{
								'icon':path+'img/emotions/xiee/1.gif',
								'value':'[xiee_1]'
							},
							{
								'icon':path+'img/emotions/xiee/2.gif',
								'value':'[xiee_2]'
							},
							{
								'icon':path+'img/emotions/xiee/3.gif',
								'value':'[xiee_3]'
							},
							{
								'icon':path+'img/emotions/xiee/4.gif',
								'value':'[xiee_4]'
							},
							{
								'icon':path+'img/emotions/xiee/5.gif',
								'value':'[xiee_5]'
							},
							{
								'icon':path+'img/emotions/xiee/6.gif',
								'value':'[xiee_6]'
							},
							{
								'icon':path+'img/emotions/xiee/7.gif',
								'value':'[xiee_7]'
							},
							{
								'icon':path+'img/emotions/xiee/8.gif',
								'value':'[xiee_8]'
							},
							{
								'icon':path+'img/emotions/xiee/9.gif',
								'value':'[xiee_9]'
							},
							{
								'icon':path+'img/emotions/xiee/10.gif',
								'value':'[xiee_10]'
							},
							{
								'icon':path+'img/emotions/xiee/11.gif',
								'value':'[xiee_11]'
							},
							{
								'icon':path+'img/emotions/xiee/12.gif',
								'value':'[xiee_12]'
							},
							{
								'icon':path+'img/emotions/xiee/13.gif',
								'value':'[xiee_13]'
							},
							{
								'icon':path+'img/emotions/xiee/14.gif',
								'value':'[xiee_14]'
							},
							{
								'icon':path+'img/emotions/xiee/15.gif',
								'value':'[xiee_15]'
							},
							{
								'icon':path+'img/emotions/xiee/16.gif',
								'value':'[xiee_16]'
							},
							{
								'icon':path+'img/emotions/xiee/17.gif',
								'value':'[xiee_17]'
							},
							{
								'icon':path+'img/emotions/xiee/18.gif',
								'value':'[xiee_18]'
							},
							{
								'icon':path+'img/emotions/xiee/19.gif',
								'value':'[xiee_19]'
							},
							{
								'icon':path+'img/emotions/xiee/20.gif',
								'value':'[xiee_20]'
							},
							{
								'icon':path+'img/emotions/xiee/21.gif',
								'value':'[xiee_21]'
							},
							{
								'icon':path+'img/emotions/xiee/22.gif',
								'value':'[xiee_22]'
							},
							{
								'icon':path+'img/emotions/xiee/23.gif',
								'value':'[xiee_23]'
							},
							{
								'icon':path+'img/emotions/xiee/24.gif',
								'value':'[xiee_24]'
							},
							{
								'icon':path+'img/emotions/xiee/25.gif',
								'value':'[xiee_25]'
							},
							{
								'icon':path+'img/emotions/xiee/26.gif',
								'value':'[xiee_26]'
							},
							{
								'icon':path+'img/emotions/xiee/27.gif',
								'value':'[xiee_27]'
							},
							{
								'icon':path+'img/emotions/xiee/28.gif',
								'value':'[xiee_28]'
							},
							{
								'icon':path+'img/emotions/xiee/29.gif',
								'value':'[xiee_29]'
							},
							{
								'icon':path+'img/emotions/xiee/30.gif',
								'value':'[xiee_30]'
							},
							{
								'icon':path+'img/emotions/xiee/31.gif',
								'value':'[xiee_31]'
							},
							{
								'icon':path+'img/emotions/xiee/32.gif',
								'value':'[xiee_32]'
							},
							{
								'icon':path+'img/emotions/xiee/33.gif',
								'value':'[xiee_33]'
							}
		            ]
		        },
		        'xiee2': {
		            title: '邪恶2', 
		            data: [
							{
								'icon':path+'img/emotions/xiee/33.gif',
								'value':'[xiee_33]'
							},
							{
								'icon':path+'img/emotions/xiee/34.gif',
								'value':'[xiee_34]'
							},
							{
								'icon':path+'img/emotions/xiee/35.gif',
								'value':'[xiee_35]'
							},
							{
								'icon':path+'img/emotions/xiee/36.gif',
								'value':'[xiee_36]'
							},
							{
								'icon':path+'img/emotions/xiee/37.gif',
								'value':'[xiee_37]'
							},
							{
								'icon':path+'img/emotions/xiee/38.gif',
								'value':'[xiee_38]'
							},
							{
								'icon':path+'img/emotions/xiee/39.gif',
								'value':'[xiee_39]'
							},
							{
								'icon':path+'img/emotions/xiee/40.gif',
								'value':'[xiee_40]'
							},
							{
								'icon':path+'img/emotions/xiee/41.gif',
								'value':'[xiee_41]'
							},
							{
								'icon':path+'img/emotions/xiee/42.gif',
								'value':'[xiee_42]'
							},
							{
								'icon':path+'img/emotions/xiee/43.gif',
								'value':'[xiee_43]'
							},
							{
								'icon':path+'img/emotions/xiee/44.gif',
								'value':'[xiee_44]'
							},
							{
								'icon':path+'img/emotions/xiee/45.gif',
								'value':'[xiee_45]'
							},
							{
								'icon':path+'img/emotions/xiee/46.gif',
								'value':'[xiee_46]'
							},
							{
								'icon':path+'img/emotions/xiee/47.gif',
								'value':'[xiee_47]'
							},
							{
								'icon':path+'img/emotions/xiee/48.gif',
								'value':'[xiee_48]'
							},
							{
								'icon':path+'img/emotions/xiee/49.gif',
								'value':'[xiee_49]'
							},
							{
								'icon':path+'img/emotions/xiee/50.gif',
								'value':'[xiee_50]'
							},
							{
								'icon':path+'img/emotions/xiee/51.gif',
								'value':'[xiee_51]'
							},
							{
								'icon':path+'img/emotions/xiee/52.gif',
								'value':'[xiee_52]'
							},
							{
								'icon':path+'img/emotions/xiee/53.gif',
								'value':'[xiee_53]'
							},
							{
								'icon':path+'img/emotions/xiee/54.gif',
								'value':'[xiee_54]'
							},
							{
								'icon':path+'img/emotions/xiee/55.gif',
								'value':'[xiee_55]'
							},
							{
								'icon':path+'img/emotions/xiee/56.gif',
								'value':'[xiee_56]'
							},
							{
								'icon':path+'img/emotions/xiee/57.gif',
								'value':'[xiee_57]'
							},
							{
								'icon':path+'img/emotions/xiee/58.gif',
								'value':'[xiee_58]'
							},
							{
								'icon':path+'img/emotions/xiee/59.gif',
								'value':'[xiee_59]'
							},
							{
								'icon':path+'img/emotions/xiee/60.gif',
								'value':'[xiee_60]'
							},
							{
								'icon':path+'img/emotions/xiee/61.gif',
								'value':'[xiee_61]'
							},
							{
								'icon':path+'img/emotions/xiee/62.gif',
								'value':'[xiee_62]'
							},
							{
								'icon':path+'img/emotions/xiee/63.gif',
								'value':'[xiee_63]'
							},
							{
								'icon':path+'img/emotions/xiee/64.gif',
								'value':'[xiee_64]'
							},
							{
								'icon':path+'img/emotions/xiee/65.gif',
								'value':'[xiee_65]'
							},
							{
								'icon':path+'img/emotions/xiee/66.gif',
								'value':'[xiee_66]'
							}
		            ]
		        },
		        'xiee3': {
		            title: '邪恶3', 
		            data: [
							{
								'icon':path+'img/emotions/xiee/67.gif',
								'value':'[xiee_67]'
							},
							{
								'icon':path+'img/emotions/xiee/68.gif',
								'value':'[xiee_68]'
							},
							{
								'icon':path+'img/emotions/xiee/69.gif',
								'value':'[xiee_69]'
							},
							{
								'icon':path+'img/emotions/xiee/70.gif',
								'value':'[xiee_70]'
							},
							{
								'icon':path+'img/emotions/xiee/71.gif',
								'value':'[xiee_71]'
							},
							{
								'icon':path+'img/emotions/xiee/72.gif',
								'value':'[xiee_72]'
							},
							{
								'icon':path+'img/emotions/xiee/73.gif',
								'value':'[xiee_73]'
							},
							{
								'icon':path+'img/emotions/xiee/74.gif',
								'value':'[xiee_74]'
							},
							{
								'icon':path+'img/emotions/xiee/75.gif',
								'value':'[xiee_75]'
							},
							{
								'icon':path+'img/emotions/xiee/76.gif',
								'value':'[xiee_76]'
							},
							{
								'icon':path+'img/emotions/xiee/77.gif',
								'value':'[xiee_77]'
							},
							{
								'icon':path+'img/emotions/xiee/78.gif',
								'value':'[xiee_78]'
							},
							{
								'icon':path+'img/emotions/xiee/79.gif',
								'value':'[xiee_79]'
							},
							{
								'icon':path+'img/emotions/xiee/80.gif',
								'value':'[xiee_80]'
							},
							{
								'icon':path+'img/emotions/xiee/81.gif',
								'value':'[xiee_81]'
							},
							{
								'icon':path+'img/emotions/xiee/82.gif',
								'value':'[xiee_82]'
							},
							{
								'icon':path+'img/emotions/xiee/83.gif',
								'value':'[xiee_83]'
							},
							{
								'icon':path+'img/emotions/xiee/84.gif',
								'value':'[xiee_84]'
							},
							{
								'icon':path+'img/emotions/xiee/85.gif',
								'value':'[xiee_85]'
							},
							{
								'icon':path+'img/emotions/xiee/86.gif',
								'value':'[xiee_86]'
							},
							{
								'icon':path+'img/emotions/xiee/87.gif',
								'value':'[xiee_87]'
							},
							{
								'icon':path+'img/emotions/xiee/88.gif',
								'value':'[xiee_88]'
							},
							{
								'icon':path+'img/emotions/xiee/89.gif',
								'value':'[xiee_89]'
							},
							{
								'icon':path+'img/emotions/xiee/90.gif',
								'value':'[xiee_90]'
							},
							{
								'icon':path+'img/emotions/xiee/91.gif',
								'value':'[xiee_91]'
							},
							{
								'icon':path+'img/emotions/xiee/92.gif',
								'value':'[xiee_92]'
							},
							{
								'icon':path+'img/emotions/xiee/93.gif',
								'value':'[xiee_93]'
							},
							{
								'icon':path+'img/emotions/xiee/94.gif',
								'value':'[xiee_94]'
							},
							{
								'icon':path+'img/emotions/xiee/95.gif',
								'value':'[xiee_95]'
							},
							{
								'icon':path+'img/emotions/xiee/96.gif',
								'value':'[xiee_96]'
							},
							{
								'icon':path+'img/emotions/xiee/97.gif',
								'value':'[xiee_97]'
							},
							{
								'icon':path+'img/emotions/xiee/98.gif',
								'value':'[xiee_98]'
							},
							{
								'icon':path+'img/emotions/xiee/99.gif',
								'value':'[xiee_99]'
							},
							{
								'icon':path+'img/emotions/xiee/100.gif',
								'value':'[xiee_100]'
							}  
		            ]
		        } 
		};
});

addEvent(document,"copy", function (n) {
	var isAdmin = $("#is-admin");
	if(isAdmin.length>0 && isAdmin.val()=="yes"){
		 return false;
	} 
	
	var isCopy = $("#isCopy").val();
	if(isCopy=="true"){
		return false;
	}
	
    var t = n.clipboardData || window.clipboardData, e = window.getSelection().toString();
    if (t && e && !(e.length < 18)) {
      n.preventDefault();
      var o = ["作者:Yangtzeriver", "来自:Yangtzeriver's blog", "链接:" + window.location.href, "", e];
      t.setData("text/html", o.join("<br>")), t.setData("text/plain", o.join("\n"))
    }
});
function addEvent(obj,ev,fn){
	if(obj.attachEvent){
		obj.attachEvent('on'+ev,fn);
	}else{
		obj.addEventListener(ev,fn,false);
	}
}


function imgReponsoeAuto(){
	var showWidth = $(".left-main").width();
	
	$("reponseimg").each(function(){
		var imgArrayStr = $(this).attr("src");
		var reponseImgSrc = getImgBySize(showWidth,imgArrayStr);
		
		$(this).after("<img style=\"max-width:100%;\" src=\""+reponseImgSrc+"\"/>")
		       .remove();
	});
}

function getImgBySize(imgWidth,imgArrayStr){
	var imgArray = imgArrayStr.split("#");
	
	for(var i=0;i<imgArray.length;i++){
		if(imgWidth>=700 && imgArray[i].indexOf("_850.jpg")>0){
			return imgArray[i];
		}
		
		if(imgWidth>=500 && imgWidth<700 && imgArray[i].indexOf("_650.jpg")>0){
			return imgArray[i];
		}
		
		if(imgWidth<500 && imgArray[i].indexOf("_450.jpg")>0){
			return imgArray[i];
		}
	}
	
	return imgArray[0];
}

function getIEVersion(){
	var browser = navigator.appName;
	var b_version = navigator.appVersion;
	var version = b_version.split(";");
	
	if(browser == "Microsoft Internet Explorer" && version.length>0){
		return parseInt(version[1].replace(/[ ]/g, "").replace(/MSIE/g, ""));
	}
	
	return 0;
}