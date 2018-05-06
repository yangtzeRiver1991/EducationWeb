<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<div class="navbar navbar-fixed-bottom">
	<div class="navbar-inner navbar-innerb footer-div">
	   <div class="copyright">
		 &copy;2016&nbsp;Yangtzeriver's Blog All Rights Reserved
	   </div>

	   <div class="sys-setup">
	      <a href="javascript:void(0)" id="sys-setup-link"><i class="icon-cog"></i>${tag}</a>
	   </div>
	   
	   <div class="icp-query-link"><a href="http://www.miibeian.gov.cn" rel="nofollow" target="_blank">渝ICP备15006603号-2</a></div>	
	</div>
</div>
<input type="hidden" id="path" value="<%=basePath%>"/>

<!-- 二维码对话框 -->
<div class="modal fade me-weixin-modal" id="me-weixin-modal" tabindex="-1" role="dialog" aria-labelledby="me-weixin-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
	 <div class="modal-dialog">
	    <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	            <h4 class="modal-title me-weixin-title">${languageConfigParameter.blog_index_aboutme_scan_qrCode}</h4>
	         </div>
	         
	         <div class="modal-body me-weixin" id="me-weixin-modal-body"></div>
	    </div>
	</div>
</div>

<!-- 系统设置对话框 -->
<div class="modal fade sys-setup-modal" id="sys-setup-modal" tabindex="-1" role="dialog" aria-labelledby="sys-setup-modal-label" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
  <div class="modal-dialog">
    <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title sys-setup-title">${languageConfigParameter.blog_index_system_config_title}</h4>
         </div>
         
         <div class="modal-body">
            <div class="form-inline">
		        <span class="label label-required float-left">${languageConfigParameter.blog_index_label_current_language}:</span>
				
				<c:choose>
				   <c:when test="${languageConfigParameter.language=='cn'}">
				       <span class="float-left margin-top3">
							<span class="pay_list_c1">
							   <input id="language_cn" class="opacityclass" name="language-config" value="cn"  type="radio" checked="checked"/>
							</span>
							<label for="language_cn">${languageConfigParameter.blog_index_radio_language_cn}</label>
				        </span>	
			            <span class="float-left margin-top3">
							<span class="pay_list_c1">
							   <input id="language_en" class="opacityclass" name="language-config" value="en"  type="radio"/>
							</span>
							<label for="language_en">${languageConfigParameter.blog_index_radio_language_en}</label>
				        </span>	
				   </c:when>
				   
				   <c:otherwise>
				        <span class="float-left margin-top3">
							<span class="pay_list_c1">
							   <input id="language_cn" class="opacityclass" name="language-config" value="cn"  type="radio"/>
							</span>
							<label for="language_cn">${languageConfigParameter.blog_index_radio_language_cn}</label>
				        </span>	
			            <span class="float-left margin-top3">
							<span class="pay_list_c1">
							   <input id="language_en" class="opacityclass" name="language-config" value="en"  type="radio" checked="checked"/>
							</span>
							<label for="language_en">${languageConfigParameter.blog_index_radio_language_en}</label>
				        </span>	
				   </c:otherwise>
				</c:choose> 
		     </div>
		     
		     <div class="form-inline clear-both margin-top50">
		        <span class="label label-required float-left margin-top2">${languageConfigParameter.blog_index_label_validate_code}:</span>&nbsp;
		        <input name="verificationCode" type="text" class="form-control verification-code-input" id="sys-setup-verificationCode"/>
		     </div>
         </div>
         
         <div class="modal-footer">
            <input type="hidden" id="original-language-config" value="${languageConfigParameter.language}"/>
            <button type="button" class="btn btn-default" data-dismiss="modal">${languageConfigParameter.blog_index_button_close}</button>
            <button type="button" class="btn btn-primary" id="sys-setup-submit">${languageConfigParameter.blog_index_button_submit}</button>
         </div>
    </div>
  </div>
</div>

<input type="hidden" id="isCopy" value="${param.isCopy}"/>
<script type="text/javascript">
   var commentSwitch = "${systemConfig.comment_switch}";
   var commentSizeLimit = "${systemConfig.comment_content_word_limit}";
   var imgSizeLimit = "${systemConfig.comment_content_img_limit}";
   var attachmentDownloadSwitch= "${systemConfig.attachment_download_switch}";

   var blog_common_loading_prompt = "${languageConfigParameter.blog_common_loading_prompt}";
   var blog_common_system_busy_prompt = "${languageConfigParameter.blog_common_system_busy_prompt}";
   var blog_index_language_no_switch_prompt = "${languageConfigParameter.blog_index_language_no_switch_prompt}";
   
   var blog_index_validate_code_input_empty_prompt = "${languageConfigParameter.blog_index_validate_code_input_empty_prompt}";
   var blog_index_validate_code_input_illegal_verification_code_prompt = "${languageConfigParameter.blog_index_validate_code_input_illegal_verification_code_prompt}";

  
   var blog_index_pager_previous = "${languageConfigParameter.blog_index_pager_previous}";
   var blog_index_pager_next = "${languageConfigParameter.blog_index_pager_next}";
   var blog_index_pager_no = "${languageConfigParameter.blog_index_pager_no}";
   var blog_index_pager_page = "${languageConfigParameter.blog_index_pager_page}";
   var blog_index_pager_all = "${languageConfigParameter.blog_index_pager_all}";
   var blog_index_pager_all_page = "${languageConfigParameter.blog_index_pager_all_page}";
   
   var blog_index_comment_floor = "${languageConfigParameter.blog_index_comment_floor}";
   var blog_index_comment_at = "${languageConfigParameter.blog_index_comment_at}";
   var blog_index_comment_lower = "${languageConfigParameter.blog_index_comment_lower}";
   var blog_index_comment_empty = "${languageConfigParameter.blog_index_comment_empty}";
   var blog_index_comment_disabled_prompt = "${languageConfigParameter.blog_index_comment_disabled_prompt}";
   var blog_index_comment_agree = "${languageConfigParameter.blog_index_comment_agree}";
   var blog_index_comment_against = "${languageConfigParameter.blog_index_comment_against}";
   
   var blog_index_comment_switch_off = "${languageConfigParameter.blog_index_comment_switch_off}";
   var blog_index_comment_input_empty_promot = "${languageConfigParameter.blog_index_comment_input_empty_promot}";
   var blog_index_comment_input_too_many_emotions = "${languageConfigParameter.blog_index_comment_input_too_many_emotions}";
   var blog_index_comment_input_too_many_comment_content = "${languageConfigParameter.blog_index_comment_input_too_many_comment_content}";
   
   var blog_index_attachment_download_switch_off = "${languageConfigParameter.blog_index_attachment_download_switch_off}";
   var blog_index_file_download_countdown = "${languageConfigParameter.blog_index_file_download_countdown}";
</script>