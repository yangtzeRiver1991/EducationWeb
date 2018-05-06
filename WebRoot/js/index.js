$(document).ready(function(){
	//search input begin
	$(".search-button").click(function(){
		var searchInput = $(".search-input").val();
		var regExp = new RegExp("^[A-Za-z0-9\u4E00-\u9FA5\uF900-\uFA2D]{1,100}$");
		
		if(regExp.test(searchInput)){
			$("#page-form").children("input[name='searchContent']").val(searchInput)
			               .parent().children("input[name='childTag']").val("")
                           .parent().children("input[name='nowPageNum']").val(1)
                           .parent().submit();
		}
	});
	
	$.fn.typeahead.Constructor.prototype.blur = function() {
		var that = this;
		setTimeout(function () { that.hide();}, 5000);
    };
    
	$(".search-input").typeahead({
		source: function (query, process) {
			var regExp = new RegExp("^[A-Za-z0-9\u4E00-\u9FA5\uF900-\uFA2D]{1,100}$");
			
			if(regExp.test(query)){
				$.post(path+"searchTitle.do", {query:query}, function (data) {
		            process(data.list);
		        });
			}
	    },
	    highlighter: function(item) {
	    	var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
	        return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	            return '<strong style=\"color:red;\">' + match + '</strong>';
	        });
	    },
	    updater: function(item) {
	    	window.location = path+"detailByTitle.do?title="+encodeURI(encodeURI(item));
	    }
	});
	//search input end
	
	
	//document link begin
	$(".doc-title-linkto-detail>a").click(function(){
		window.location = path+"detail.do?documentId="+$(this).attr("documentId");
	});
	//document link end
	
	
	//pagination begin
	$(".page-button").click(function(){
		$("#page-form").children("input[name='nowPageNum']").val($(this).attr("nowPageNum"))
		               .parent().submit();
	});
	
	$(".pager-select-ul>li").live('click',function(){
        $(this).parent().css("display","none");
		
		var nowPageNum = parseInt($(".pager-select").children(".num").text());
		var linkNum = parseInt($(this).attr("pagevalue"));
		
  		if(nowPageNum!=linkNum){
  			$("#page-form").children("input[name='nowPageNum']").val(linkNum)
                           .parent().submit();
  		}
	});
	//pagination end
	
	showCarouselImg();
	
	
	function showCarouselImg(){
		var myCarousel = $("#myCarousel");
		
		if(myCarousel.length>0){
			$("#myCarousel").css("display","block");
			
			imgReponsoeAuto();
			
			$("#myCarousel").carousel({
		        interval: 3000,
		        pause:"hover",
		        wrap:true
		    });
			$("#myCarousel").touchwipe({
			    wipeLeft:function(){
			    	$("#myCarousel").carousel('next');
			    },
			    wipeRight:function(){
			    	$("#myCarousel").carousel('prev');
			    }
			});
			$("#myCarousel").mouseover(function(){
				$(".carousel-control").addClass("carousel-control-show").removeClass("carousel-control-hide");
			}).mouseout(function(){
				$(".carousel-control").addClass("carousel-control-hide").removeClass("carousel-control-show");
			});
			$(".carousel-inner>.item").click(function(){
				window.location = path+"detail.do?documentId="+$(this).attr("documentId");
			});
	    }
	}
					               
});
