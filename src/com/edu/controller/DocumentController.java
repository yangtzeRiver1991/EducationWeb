package com.edu.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.Attachment;
import com.edu.entity.Comment;
import com.edu.entity.Document;
import com.edu.entity.Msg;
import com.edu.entity.PageResult;
import com.edu.init.ErrorCodeInit;
import com.edu.service.AttachmentService;
import com.edu.service.CommentService;
import com.edu.service.DocumentService;
import com.edu.util.AccessUtils;
import com.edu.util.ArrayUtil;
import com.edu.util.DateUtil;
import com.edu.util.ImageCutterUtil;
import com.edu.util.MD5Code;
import com.edu.util.StringUtils;
import com.edu.validate.DocumentValidate;


@Controller
public class DocumentController {
    private Logger logger = Logger.getLogger(DocumentController.class);
    
    @Resource
    private DocumentService documentService;
    
    @Resource
    private CommentService commentService;
    
    @Resource
    private AttachmentService attachmentService;
    
    @RequestMapping(value = "doclist")  
    public String doclist(String searchContent,String accessFlag,String currentHref,String childTag,Integer nowPageNum,Integer pageSize,HttpServletRequest request,Model model) {
    	
        try{
        	//加载新闻
            List<Document> newDocuments = documentService.findDocumentByTag("新闻");
            model.addAttribute("newDocuments", newDocuments);
            
            //加载课程
            List<Document> courseDocuments = documentService.findDocumentByTag("课程");
            model.addAttribute("courseDocuments", courseDocuments);
            
            //加载公示
            List<Document> hotDocuments = documentService.findHotDocumentList();
            model.addAttribute("hotDocuments", hotDocuments);
            
            //加载置顶文章
            List<Document> topDocuments = documentService.findTopDocumentList();
            model.addAttribute("topDocuments", topDocuments);

            return "WEB-INF/index";
        }catch(Exception e){
            e.printStackTrace();
            logger.error("加载首页文章列表失败,搜索内容="+searchContent+",报错信息为:"+e.getMessage());
            
            model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("docuemntListIndex").toString()));
            return "WEB-INF/error";
        }
        
    }
    
    
    @RequestMapping(value = "detail")  
    public String detail(Integer documentId,HttpServletRequest request,Model model) {
        try{
        	if(documentId==null){
        		AccessUtils.recordIllegalArgumentAccess(request);
        		model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("illegalArgument").toString()));
                return "WEB-INF/error";
        	}
        	
            
            Document document = documentService.findDocument(documentId,0);
            
            if(document == null){
            	model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("docuemntDetailById").toString()));
                return "WEB-INF/error";
            }
            
            String currentHref = document.getTags().split(",")[0];
            
            //加载附件
            List<Attachment> attachments = attachmentService.findAttachmentList(documentId);
            document.setAttachments(attachments);
            
            //加载评论
            PageResult<Comment> pageResult = commentService.findCommentList(document.getId(), 1, 5,"asc"); 
            document.setCommentList(pageResult);
            
            //加载热门文章
            List<Document> hotDocuments = documentService.findHotDocumentList();
            
            //加载文章上下文
            Map<String, Object> upDownDocument = documentService.findUpDownDocument(documentId, currentHref);
            document.setUpDownDocument(upDownDocument);
            
            //更新文章浏览次数
            documentService.updateLookNum(documentId);
                  
            //图片响应式适配
            if(!StringUtils.isEmpty(document.getUploadImgs())){
            	document.setContent(ImageCutterUtil.imgAuto(document.getUploadImgs(), document.getContent()));
            }
            
            logger.info("根据文章标题加载文章成功,文章id="+documentId);
            
            model.addAttribute("document", document); 
            model.addAttribute("currentHref", currentHref);
            model.addAttribute("accessFlag", "yes");
            model.addAttribute("hotDocuments", hotDocuments);
            
            return "WEB-INF/detail";
        }catch(Exception e){
            e.printStackTrace();
            logger.error("根据文章标题加载文章失败,文章id="+documentId+",报错信息为:"+e.getMessage());
            
            model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("docuemntDetailById").toString()));
            return "WEB-INF/error";
        }
    }
    
    @RequestMapping(value = "detailByTitle")  
    public String detailByTitle(String title,HttpServletRequest request,Model model) {
        try{
            Msg msg = DocumentValidate.searchContentValidate(title);
            if(msg != null){
            	AccessUtils.recordIllegalArgumentAccess(request);
                model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("illegalArgument").toString()));
                return "WEB-INF/error";
            }
          
            title = java.net.URLDecoder.decode(title,"UTF-8"); 
            Document document = documentService.findDocument(title);  
            
            if(document == null){
            	model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("docuemntDetailByTitle").toString()));
                return "WEB-INF/error";
            }
            
            String currentHref = document.getTags().split(",")[0];
            
            //加载附件
            List<Attachment> attachments = attachmentService.findAttachmentList(document.getId());
            document.setAttachments(attachments);
            
            //加载评论
            PageResult<Comment> pageResult = commentService.findCommentList(document.getId(), 1, 5,"asc"); 
            document.setCommentList(pageResult);
            
            //加载热门文章
            List<Document> hotDocuments = documentService.findHotDocumentList();
            
            //加载文章上下文
            Map<String, Object> upDownDocument = documentService.findUpDownDocument(document.getId(), currentHref);
            document.setUpDownDocument(upDownDocument);
            
            //更新文章浏览次数
            documentService.updateLookNum(document.getId());
                  
            //图片响应式适配
            if(!StringUtils.isEmpty(document.getUploadImgs())){
            	document.setContent(ImageCutterUtil.imgAuto(document.getUploadImgs(), document.getContent()));
            }
            
            logger.info("根据文章标题加载文章成功,文章id="+document.getId());
            
            model.addAttribute("document", document); 
            model.addAttribute("currentHref", currentHref);
            model.addAttribute("accessFlag", "yes");
            model.addAttribute("hotDocuments", hotDocuments);
            
            return "WEB-INF/detail";
        }catch(Exception e){
            e.printStackTrace();
            logger.error("根据文章标题加载文章失败,文章名称="+title+",报错信息为:"+e.getMessage());
            
            model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("docuemntDetailByTitle").toString()));
            return "WEB-INF/error";
        }
    }

    
    @RequestMapping(value = "searchTitle")  
    @ResponseBody
    public Msg searchTitle(String query,HttpServletRequest request) {
        Msg msg = DocumentValidate.searchContentValidate(query);
        if(msg != null){
            return msg;
        }
        
        Msg newMsg = new Msg(0,"提示标题加载成功",documentService.findTitle(query));
        
        return newMsg;
    }
    
    
    
    @RequestMapping(value = "admin/addDocument")  
    @ResponseBody
    public Msg addDocument(@ModelAttribute Document document,HttpServletRequest request) {
        try{
        	if(document==null){
        		 Msg msg = new Msg(0,"添加文章失败");
                 return msg;
        	}
        	
        	document.setLookNum(0);
        	document.setCommentNum(0);
        	document.setCreateTime(DateUtil.getTimeTwo());
        	document.setCreateIp(AccessUtils.getIpAddr(request));
        	document.setStatus(0);
        	document.setIsHot(0);
        	document.setIsTop(0);
        	
            documentService.insertDocument(document);
            
            logger.info("添加文章成功,文章id="+document.getId());
            
            Msg msg = new Msg(0,"添加文章成功");
            return msg;
        }catch(Exception e){
            e.printStackTrace();
            
            logger.error("添加文章失败,原因为:"+e.getMessage());
            Msg msg = new Msg(1,"添加文章失败");
            return msg;
        }
    }
    
    
    @RequestMapping(value = "admin/login")  
    public String adminLogin(String accessCode,HttpServletRequest request) {
    	if(StringUtils.isEmpty(accessCode)){
    		AccessUtils.recordIllegalArgumentAccess(request);
    		return "redirect:/admin/login.jsp";
    	}
    	
    	if(! new MD5Code().getMD5ofStr(accessCode).equals("49A9C3758A9E42CF6CEBB62DC0DF02AB")){
    		AccessUtils.recordIllegalArgumentAccess(request);
    		return "redirect:/admin/login.jsp";
    	}
    	
    	request.getSession().setAttribute("accessAdmit", "yes");
    	
    	return "redirect:/admin/main.jsp";
    }
    
    
    @RequestMapping(value = "admin/documentList")  
    @ResponseBody
    public PageResult<Document> doucmentList(@ModelAttribute Document document,String createTime,String endTime,Integer offset,Integer limit,HttpServletRequest request) {
    	try{
    		PageResult<Document> pageResult = documentService.findDocumentList(document, createTime, endTime, offset, limit);
    				
    		logger.info("后台加载文章列表成功");
    		return pageResult;
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台加载文章列表失败,原因为:"+e.getMessage());
    		
    		PageResult<Document> pageResult = new PageResult<Document>(null,0,1);
    		
    		return pageResult;
    	}
    }
    
    
    
    @RequestMapping(value = "admin/hotOperation")  
    @ResponseBody
    public Msg hotOperation(String documentIdArray,Integer operation,HttpServletRequest request) {
    	if(StringUtils.isEmpty(documentIdArray) || operation==null){
    		return new Msg(1,"操作失败");
    	}
    	
    	try{
    		if(documentIdArray.contains(",")){
    			Integer[] documentIds = ArrayUtil.convertToIntegerArray(documentIdArray.split(","));
    			
    			documentService.updateDocumentIsHot(documentIds, operation);
    		}else{
    			documentService.updateDocumentIsHot(Integer.parseInt(documentIdArray), operation);
    		}
    		
            return new Msg(0,"操作成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台更新文章热门操作失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"操作失败");
    	}
    }
    
    
    @RequestMapping(value = "admin/abandonTop")  
    @ResponseBody
    public Msg abandonTop(Integer documentId,HttpServletRequest request) {
    	if(documentId==null){
    		return new Msg(1,"操作失败");
    	}
    	
    	try{
    		String path = request.getSession().getServletContext().getRealPath("upload");  
    		Document document = documentService.findDocument(documentId,null);
    		
            if(document!=null && !StringUtils.isEmpty(document.getCarouselImg())){
            	String[] imgWayArray = document.getCarouselImg().split("#");
            	
            	for(String imgWay:imgWayArray){
            		File tempFile = new File(path, imgWay);  
                    if(tempFile.exists()){ 
                    	tempFile.delete();
                    }
            	}
            }
    		
    	    documentService.updateDocumentIsTop(documentId, 0, "");
    		
            return new Msg(0,"操作成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台更新文章置顶操作失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"操作失败");
    	}
    }
    
    
    @RequestMapping(value = "admin/useOperation")  
    @ResponseBody
    public Msg useOperation(String documentIdArray,Integer operation,HttpServletRequest request) {
    	if(StringUtils.isEmpty(documentIdArray) || operation==null){
    		return new Msg(1,"操作失败");
    	}
    	
    	try{
    		if(documentIdArray.contains(",")){
    			Integer[] documentIds = ArrayUtil.convertToIntegerArray(documentIdArray.split(","));
    			
    			documentService.updateDocumentStatus(documentIds, operation);
    		}else{
    			documentService.updateDocumentStatus(Integer.parseInt(documentIdArray), operation);
    		}
    		
            return new Msg(0,"操作成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台更新文章状态操作失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"操作失败");
    	}
    }
    
    
    @RequestMapping(value = "admin/editDocument")  
    public String editDocument(Integer documentId,HttpServletRequest request,Model model) {
    	if(documentId != null){
    		Document document = documentService.findDocument(documentId,null);
    		
    		if(document != null){
    			model.addAttribute("document", document);
    		}
    	}
    	
    	return "admin/documentEdit";
    }
    
    @RequestMapping(value = "admin/editDocumentResult")  
    @ResponseBody
    public Msg editDocumentResult(@ModelAttribute Document document,HttpServletRequest request) {
    	try{
        	if(document==null){
        		 Msg msg = new Msg(0,"更新文章失败");
                 return msg;
        	}
            documentService.updateDocument(document);
            
            logger.info("更新文章成功,文章id="+document.getId());
            
            Msg msg = new Msg(0,"更新文章成功");
            return msg;
        }catch(Exception e){
            e.printStackTrace();
            
            logger.error("更新文章失败,原因为:"+e.getMessage());
            Msg msg = new Msg(1,"更新文章失败");
            return msg;
        }
    }
    
    
    @RequestMapping(value = "aboutMe")  
    public String aboutMe(HttpServletRequest request,Model model) {	
    	model.addAttribute("mobileQQ_link", "true");
    	model.addAttribute("accessFlag", "yes");
    	model.addAttribute("currentHref", "aboutme");
    	return "WEB-INF/aboutMe";
    }
    
    @RequestMapping(value = "friendlyLink")  
    public String friendlyLink(HttpServletRequest request,Model model) {
    	model.addAttribute("accessFlag", "yes");
    	model.addAttribute("currentHref", "friendlyLink");
    	return "WEB-INF/friendlyLink";
    }
    
    @RequestMapping(value = "hotTags")  
    public String hotTags(HttpServletRequest request,Model model) {
    	model.addAttribute("accessFlag", "yes");
    	model.addAttribute("currentHref", "hotTags");
    	
    	return "WEB-INF/hotTags";
    }
    
    @RequestMapping(value = "hotDocuments")  
    public String hotDocuments(HttpServletRequest request,Model model) {
    	model.addAttribute("accessFlag", "yes");
    	model.addAttribute("currentHref", "hotDocuments");
    	
    	//加载热门文章
        List<Document> hotDocuments = documentService.findHotDocumentList();
        model.addAttribute("hotDocuments", hotDocuments);
        
    	return "WEB-INF/hotDocuments";
    }
    
    @RequestMapping(value = "admin/exit")  
    public String exit(HttpServletRequest request) {
    	request.getSession().invalidate();

    	return "redirect:/admin/login.jsp";
    }
    
}
