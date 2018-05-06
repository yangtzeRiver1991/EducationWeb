package com.edu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.entity.Attachment;
import com.edu.entity.Document;
import com.edu.entity.Msg;
import com.edu.init.SystemConfigInit;
import com.edu.service.AttachmentService;
import com.edu.service.DocumentService;
import com.edu.util.AccessUtils;
import com.edu.util.DateUtil;
import com.edu.util.FileUtil;
import com.edu.util.ImageCutterUtil;
import com.edu.util.StringUtils;
import com.edu.validate.CommonValidate;

@Controller
public class FileController {
	private Logger logger = Logger.getLogger(DocumentController.class);
	
	@Resource
    private AttachmentService attachmentService;
	@Resource
    private DocumentService documentService;
    
    @RequestMapping(value = "admin/uploadImg")  
    @ResponseBody
    public String uploadImg(@RequestParam(value = "wangEditorH5File", required = false) MultipartFile file, HttpServletRequest request) { 
        //String path = request.getSession().getServletContext().getRealPath("upload");  
    	String path = SystemConfigInit.getSystemConfig().get("upload_save_path");
        String fileName = FileUtil.getImgName(); 
        String dateDay = DateUtil.getDay();
        
        //先判断文件夹是否存在,无则新增
        File directory = new File(path+"/"+dateDay);
        if(!directory.exists()  && !directory.isDirectory()){       
            directory.mkdir();    
        }
        path += "/"+dateDay;
        
        File targetFile = new File(path, fileName+".jpg");  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
            
            StringBuffer sb = new StringBuffer(request.getContextPath()+"/upload/"+dateDay+"/"+fileName+".jpg");
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+fileName+"_850"+".jpg",850, 0))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(fileName)
            	  .append("_850")
            	  .append(".jpg");
            }
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+fileName+"_650"+".jpg",650, 0))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(fileName)
            	  .append("_650")
            	  .append(".jpg");
            }
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+fileName+"_450"+".jpg",450, 0))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(fileName)
            	  .append("_450")
            	  .append(".jpg");
            }
            
            logger.info("上传图片成功，图片名为:"+fileName+".jpg");
            return sb.toString(); 
        } catch (Exception e) {  
            e.printStackTrace(); 
            
            logger.error("上传图片失败，原因为:"+e.getMessage());
            return "error|"+e.getMessage();
        }  
        
    }  
    
    
    @RequestMapping(value = "admin/uploadAttachment") 
    @ResponseBody
    public Msg uploadAttachment(@RequestParam(value = "attachment", required = false) MultipartFile file,Integer documentId, HttpServletRequest request) { 
    	//String path = request.getSession().getServletContext().getRealPath("upload"); 
    	String path = SystemConfigInit.getSystemConfig().get("upload_save_path");
        String fileName = FileUtil.getFileName(file); 
        String newFileName = FileUtil.formatFileName(file,true);
        String dateDay = DateUtil.getDay();
        Map<String,Object> attachmentSizeInfo = FileUtil.formatFileSize(file);
         
        
        try {
			//附件相关信息插入数据库
			Attachment attachment = new Attachment();
			attachment.setDocumentId(documentId);
			attachment.setCode(FileUtil.getImgName());
			attachment.setDownloadCount(0);
			attachment.setFileType(FileUtil.getFileType(file));
			attachment.setName(fileName);
			attachment.setSize(Double.parseDouble(attachmentSizeInfo.get("size").toString()));
			attachment.setUnit(attachmentSizeInfo.get("unit").toString());
			attachment.setUploadTime(DateUtil.getTimeTwo());
			attachment.setSaveUrl(dateDay+"/"+newFileName);
			
			if(attachmentService.insertAttachment(attachment) != null){
				//先判断文件夹是否存在,无则新增
		        File directory = new File(path+"/"+dateDay);
		        if(!directory.exists()  && !directory.isDirectory()){       
		            directory.mkdir();    
		        }
		        path += "/"+dateDay;
		        
		        File targetFile = new File(path, newFileName);  
		        if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }
		        
				file.transferTo(targetFile);
				
				logger.info("上传附件成功，附件名为:"+fileName);
				return new Msg(0,"上传附件成功，附件名为:"+fileName,attachment);
			}
			
			logger.info("上传附件失败，附件名为:"+fileName);
			return new Msg(1,"上传附件失败，附件名为:"+fileName);
		} catch (Exception e) {  
            e.printStackTrace(); 
            
            logger.error("上传附件失败，原因为:"+e.getMessage());
            return new Msg(1,"上传附件失败，附件名为:"+fileName);
        }  
    }
    
    @RequestMapping(value = "admin/findDocAttachment") 
    @ResponseBody
    public Msg findDocAttachment(Integer documentId, HttpServletRequest request) { 
    	try{
    		List<Attachment> docAttachments = attachmentService.findAttachmentList(documentId);

    		logger.info("加载附件列表成功，documentId="+documentId);
    		return new Msg(0,"加载附件列表成功",docAttachments);
    	}catch(Exception e){
    		e.printStackTrace();
    		
    		logger.error("加载附件列表失败，原因为:"+e.getMessage());
    		return new Msg(1,"加载附件列表失败,原因为:"+e.getMessage());
    	}
    }
    
    
    @RequestMapping(value = "admin/deleteDocAttachment") 
    @ResponseBody
    public Msg deleteDocAttachment(Integer attachmentId,String code,HttpServletRequest request) { 
    	try{
    		Attachment attachment = attachmentService.findAttachment(code);
    		if(attachment == null){
    			return new Msg(0,"删除附件成功");
    		}

    		Integer i = attachmentService.deleteAttachment(attachmentId,attachment.getDocumentId(), code);

    		if(i == 1){
    			//删除文件附件
    			//String path = request.getSession().getServletContext().getRealPath("upload"); 
    			String path = SystemConfigInit.getSystemConfig().get("upload_save_path");
    			File file = new File(path + "/" +attachment.getSaveUrl());
    			
    			if(file.exists()){
    				file.delete();
    			}
    			
    			logger.info("删除附件成功");
        		return new Msg(0,"删除附件成功");
    		}

    		logger.info("删除附件失败");
    		return new Msg(1,"删除附件失败");
    	}catch(Exception e){
    		e.printStackTrace();
    		
    		logger.info("删除附件失败,原因为:"+e.getMessage());
    		return new Msg(1,"删除附件失败,原因为:"+e.getMessage());
    	}
    }
    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "prepareAttachmentDownload") 
    @ResponseBody
	public Msg prepareAttachmentDownload(String code,HttpServletRequest request) throws IOException {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	HashMap<String,String> systemConfig = (HashMap<String, String>) request.getServletContext().getAttribute("systemConfig");
    	String attachmentDownloadSwitch = systemConfig.get("attachment_download_switch");
    	
    	if(!"on".equals(attachmentDownloadSwitch)){
    		return new Msg(1,languageConfigParameter.get("blog_index_attachment_download_switch_off"));
    	}
    	
    	Msg msg = CommonValidate.paramValidate(code, languageConfigParameter);
    	if(msg != null){
    		AccessUtils.recordIllegalArgumentAccess(request);
    		return msg;
    	}
    	
    	Attachment attachment = attachmentService.findAttachment(code);
    	if(attachment == null){
    		msg = new Msg(1,languageConfigParameter.get("blog_index_file_not_exist"));
    		return msg;
    	}
    	
    	//验证下载时间间隔
		Date beforeUploadTime = (Date) request.getSession().getAttribute("attachmentBeforeUploadTime");
		
		if(beforeUploadTime != null){
			Date nowUploadTime = (Date) request.getSession().getAttribute("attachmentNowUploadTime");
	    	
	    	if(nowUploadTime == null){
	    		nowUploadTime = new Date();
	    		request.getSession().setAttribute("attachmentNowUploadTime",nowUploadTime);
	    	}
			
			long dateIntervals = DateUtil.calculateDateIntervals(beforeUploadTime, nowUploadTime, "second");
			long dateIntervals_new = DateUtil.calculateDateIntervals(nowUploadTime, new Date(), "second");
			int attachmentDownloadIntervalLimit = Integer.parseInt(systemConfig.get("attachment_download_interval_limit"));
			
			if(dateIntervals_new<dateIntervals+attachmentDownloadIntervalLimit){
				return new Msg(1,languageConfigParameter.get("blog_index_file_download_date_intervals_short"),dateIntervals + attachmentDownloadIntervalLimit - dateIntervals_new);
			}
		}
    	
		request.getSession().setAttribute("attachmentDownload", "yes");
    	return new Msg(0,"");
    	
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "downloadAttachment") 
    @ResponseBody
	public ResponseEntity downloadAttachment(String code,HttpServletRequest request) throws IOException {
    	HashMap<String,String> languageConfigParameter = (HashMap<String, String>) request.getSession().getAttribute("languageConfigParameter");
    	Msg msg = CommonValidate.paramValidate(code, languageConfigParameter);
    	if(msg != null){
    		AccessUtils.recordIllegalArgumentAccess(request);
    		return null;
    	}
    	
    	String is_download = (String) request.getSession().getAttribute("attachmentDownload");
    	if(!"yes".equals(is_download)){
    		return null;
    	}
    	
    	Attachment attachment = attachmentService.findAttachment(code);
    	if(attachment == null){
    		return null;
    	}
    	
    	request.getSession().setAttribute("attachmentDownload", "no");
    	request.getSession().setAttribute("attachmentBeforeUploadTime", new Date());
    	request.getSession().removeAttribute("attachmentNowUploadTime");
    	
		return attachmentService.downloadAttachment(attachment,SystemConfigInit.getSystemConfig().get("upload_save_path"), new HttpHeaders());
	}
    
    
    
    @RequestMapping(value = "admin/uploadCarousel") 
    @ResponseBody
    public Msg uploadCarousel(@RequestParam(value = "carousel", required = false) MultipartFile file,Integer documentId, HttpServletRequest request) { 
    	//String path = request.getSession().getServletContext().getRealPath("upload");  
    	String path = SystemConfigInit.getSystemConfig().get("upload_save_path");
        String newFileName = FileUtil.formatFileName(file,false);
        String dateDay = DateUtil.getDay();
         
        //先判断文件夹是否存在,无则新增
        File directory = new File(path+"/"+dateDay);
        if(!directory.exists()  && !directory.isDirectory()){       
            directory.mkdir();    
        }
        path += "/"+dateDay;
        
        File targetFile = new File(path, newFileName+".jpg");  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
            
            StringBuffer sb = new StringBuffer(request.getContextPath()+"/upload/"+dateDay+"/"+newFileName+".jpg");
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+newFileName+"_1170"+".jpg",1170, 300))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(newFileName)
            	  .append("_850")
            	  .append(".jpg");
            }
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+newFileName+"_850"+".jpg",850, 250))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(newFileName)
            	  .append("_850")
            	  .append(".jpg");
            }
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+newFileName+"_650"+".jpg",650, 200))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(newFileName)
            	  .append("_650")
            	  .append(".jpg");
            }
            
            if(!StringUtils.isEmpty(ImageCutterUtil.zoom(targetFile, path+"/"+newFileName+"_450"+".jpg",450, 150))){
            	sb.append("#")
            	  .append(request.getContextPath())
            	  .append("/upload/")
            	  .append(dateDay)
            	  .append("/")
            	  .append(newFileName)
            	  .append("_450")
            	  .append(".jpg");
            }
            
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
                   
            //更新数据
            documentService.updateDocumentIsTop(documentId, 1, sb.toString());
 
            logger.info("上传轮播图片成功，图片名为:"+newFileName+".jpg");
            
            return new Msg(0,sb.toString()); 
        } catch (Exception e) {  
            e.printStackTrace(); 
            
            logger.error("上传轮播图片失败，原因为:"+e.getMessage());
            return new Msg(1,e.getMessage()); 
        }
    }

}
