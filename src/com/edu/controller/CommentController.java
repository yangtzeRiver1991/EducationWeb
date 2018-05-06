package com.edu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.Comment;
import com.edu.entity.Msg;
import com.edu.entity.PageResult;
import com.edu.init.EmotionsInit;
import com.edu.service.CommentService;
import com.edu.util.AccessUtils;
import com.edu.util.ArrayUtil;
import com.edu.util.DateUtil;
import com.edu.util.RandomUtils;
import com.edu.util.StringUtils;
import com.edu.validate.CommentValidate;


@Controller
public class CommentController {
    private Logger logger = Logger.getLogger(CommentController.class);
    
    @Resource
    private CommentService commentService;
   
    
         
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "comment")  
    @ResponseBody  
    public Msg comment(Integer docId,String comment,String verificationCode,HttpServletRequest request) {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	HashMap<String,String> systemConfig = (HashMap<String, String>) request.getServletContext().getAttribute("systemConfig");
    	
        try{
        	if(docId == null){
        		AccessUtils.recordIllegalArgumentAccess(request);
        		return new Msg(1,"docId "+languageConfigParameter.get("blog_common_empty_param_prompt"));
        	}
        	
        	Msg msg = CommentValidate.publishCommentValidate(comment, verificationCode,languageConfigParameter,systemConfig);
        	if(msg != null){
        		AccessUtils.recordIllegalArgumentAccess(request);
        	    return msg;
        	}
        	
        	if(!verificationCode.equals(request.getSession().getAttribute("validateCode"))){
        		AccessUtils.recordIllegalArgumentAccess(request);
                Msg nowMsg = new Msg(1,languageConfigParameter.get("blog_index_validate_code_input_error_verification_code_prompt"));
                return nowMsg;
            }
            
            request.getSession().setAttribute("validateCode", RandomUtils.randomCompose(4, 50));
            
            //图片替换
            comment = EmotionsInit.switchEmotionKey(comment);
        	       
        	String ip = AccessUtils.getIpAddr(request);
        	Comment commentE = new Comment();
        	
        	commentE.setDocmentId(docId);
        	commentE.setCommentContent(comment.trim());
        	commentE.setCommentDate(DateUtil.getTimeTwo());
        	commentE.setCommentUser("网友"+AccessUtils.getAddresses(ip, "utf-8"));
        	commentE.setIp(ip);
        	commentE.setAgreeNum(0);
        	commentE.setAgainstNum(0);
        	commentE.setStatus(0);
        	
        	Comment result = commentService.insertComment(commentE);
        	
        	PageResult<Comment> pageResult = commentService.findCommentList(docId, 1, 5,"desc"); 
        	
            logger.info("发布评论成功,文章id="+docId+",评论id="+result.getId());
            
            Map<String,Object> msgResult = new HashMap<String,Object>();
            msgResult.put("pageResult", pageResult);            
            
            Msg newMsg = new Msg(0,languageConfigParameter.get("blog_index_comment_success"),msgResult);
            return newMsg;
        }catch(Exception e){
            e.printStackTrace();
            
            logger.error("发布评论失败,文章id="+docId+",原因为:"+e.getMessage());
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_fail"));
            return msg;
        }
    }     
    
    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "commentList")  
    @ResponseBody  
    public Msg commentList(Integer docId,Integer nowPageNum,String sort,HttpServletRequest request) {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	
    	try{
    		 if(docId==null){
    			 AccessUtils.recordIllegalArgumentAccess(request);
                 Msg msg = new Msg(1,"docId "+languageConfigParameter.get("blog_common_empty_param_prompt"));
                 return msg;
    		 }
    		
    	     if("new".equals(sort)){
    	         sort = "desc";
    	     }else{
    	         sort = "asc";
    	     }
    	     
    	     if(nowPageNum == null){
    	    	 nowPageNum = 1;
    	     }
    	    
    		 PageResult<Comment> pageResult = commentService.findCommentList(docId, nowPageNum, 5,sort); 
    		
    		 logger.info("获取评论列表成功,文章id="+docId);
             
             Map<String,Object> msgResult = new HashMap<String,Object>();
             msgResult.put("pageResult", pageResult);            
             
             Msg newMsg = new Msg(0,"分页成功",msgResult);
             return newMsg;
    	}catch(Exception e){
    		 e.printStackTrace();
             
             logger.error("获取评论列表失败,文章id="+docId+",原因为:"+e.getMessage());
             Msg msg = new Msg(1,"分页失败");
             return msg;
    	}
    }
    
    
    @RequestMapping(value = "admin/commentList")  
    @ResponseBody  
    public Msg adminCommentList(Integer docId,Integer nowPageNum,String sort,HttpServletRequest request) {
    	try{
	   		 if(docId==null){
	            Msg msg = new Msg(1,"文章id不能为空");
	            return msg;
	   		 }
	   		
	   	     if("new".equals(sort)){
	   	         sort = "desc";
	   	     }else{
	   	         sort = "asc";
	   	     }
	   	    
	   		 PageResult<Comment> pageResult = commentService.findCommentList(docId, nowPageNum, 5,sort); 
	   		
	   		 logger.info("获取评论列表成功,文章id="+docId);
	            
             Map<String,Object> msgResult = new HashMap<String,Object>();
             msgResult.put("pageResult", pageResult);            
            
             Msg newMsg = new Msg(0,"分页成功",msgResult);
             return newMsg;
	   	}catch(Exception e){
	   		 e.printStackTrace();
	            
	         logger.error("获取评论列表失败,文章id="+docId+",原因为:"+e.getMessage());
	         Msg msg = new Msg(1,"分页失败");
	         return msg;
	   	}
    }
    
    
    @RequestMapping(value = "admin/commentOperation")  
    @ResponseBody  
    public Msg commentOperation(String commentIdArray,Integer operation,HttpServletRequest request) {
    	if(StringUtils.isEmpty(commentIdArray) || operation==null){
    		return new Msg(1,"操作失败");
    	}
    	
    	try{
    		if(commentIdArray.contains(",")){
    			Integer[] commentIds = ArrayUtil.convertToIntegerArray(commentIdArray.split(","));
    			
    			commentService.updateCommentStatus(commentIds, operation);
    		}else{
    			commentService.updateCommentStatus(Integer.parseInt(commentIdArray), operation);
    		}
    		
            return new Msg(0,"操作成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台更新评论状态操作失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"操作失败");
    	}
    }
    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "commentAgree")  
    @ResponseBody  
    public Msg commentAgree(Integer commentId,HttpServletRequest request) {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	
    	try{
	   		 if(commentId == null){
	   			 AccessUtils.recordIllegalArgumentAccess(request);
	             Msg msg = new Msg(1,"commentId "+languageConfigParameter.get("blog_common_empty_param_prompt"));
	             return msg;
	   		 }
	   		 
	   		 Comment comment = commentService.findComment(commentId);
	   	    
	   		 if(comment == null){
	   			Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_not_exist"));
	            return msg;
	   		 }
	   		 
	   		 if(comment.getStatus()==1){
	   			Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_has_been_delete"));
	            return msg;
	   		 }
	   		 
	   		 String commentAgreeKey = AccessUtils.getIpAddr(request)+"_commentAgree_"+commentId;
	   		 String commentAgreeFlag = (String) request.getSession().getAttribute(commentAgreeKey);
	   		 
	   		 if(StringUtils.switchEmptyStr(commentAgreeFlag).equals("yes")){
	   			Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_has_been_agree"));
	            return msg;
	   		 }
	   		 
	   		 commentService.updateCommentAgreeNum(commentId);
	   		 request.getSession().setAttribute(commentAgreeKey, "yes");
	   		
	   		 logger.info("评论点赞成功,commentId="+commentId);

             return new Msg(0,languageConfigParameter.get("blog_index_comment_agree_success"));
	   	}catch(Exception e){
	   		 e.printStackTrace();
	            
	         logger.error("评论点赞成功,commentId="+commentId+",原因为:"+e.getMessage());
	         
	         return new Msg(1,languageConfigParameter.get("blog_index_comment_agree_fail"));
	   	}
    }
    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "commentAgainst")  
    @ResponseBody  
    public Msg commentAgainst(Integer commentId,HttpServletRequest request) {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	
    	try{
	   		 if(commentId == null){
	   			 AccessUtils.recordIllegalArgumentAccess(request);
	             Msg msg = new Msg(1,"commentId "+languageConfigParameter.get("blog_common_empty_param_prompt"));
	             return msg;
	   		 }
	   		 
	   		 Comment comment = commentService.findComment(commentId);
	   	    
	   		 if(comment == null){
	   			Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_not_exist"));
	            return msg;
	   		 }
	   		 
	   		 if(comment.getStatus()==1){
	   			Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_has_been_delete"));
	            return msg;
	   		 }
	   		 
	   		 String commentAgainstKey = AccessUtils.getIpAddr(request)+"_commentAgainst_"+commentId;
	   		 String commentAgainstFlag = (String) request.getSession().getAttribute(commentAgainstKey);
	   		 
	   		 if(StringUtils.switchEmptyStr(commentAgainstFlag).equals("yes")){
	   			Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_has_been_against"));
	            return msg;
	   		 }
	   		 
	   		 commentService.updateCommentAgainstNum(commentId);
	   		 request.getSession().setAttribute(commentAgainstKey, "yes");
	   		
	   		 logger.info("评论举报成功,commentId="+commentId);

             return new Msg(0,languageConfigParameter.get("blog_index_comment_against_success"));
	   	}catch(Exception e){
	   		 e.printStackTrace();
	            
	         logger.error("评论举报成功,commentId="+commentId+",原因为:"+e.getMessage());
	         
	         return new Msg(1,languageConfigParameter.get("blog_index_comment_against_fail"));
	   	}
    }
}
