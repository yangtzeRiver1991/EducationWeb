package com.edu.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
/**
 * 文件操作工具类
 * @author yangze
 *
 */
public class FileUtil {

    /**
     * 生成图片名称
     * @return
     */
    public static String getImgName(){
       return DateUtil.getTime() + RandomUtils.randomCompose(4, 10);	
    }
   
    /**
     * 获取附件名称(包含文件类型)
     * @param file
     * @return
     */
    public static String getFileName(MultipartFile file){
        if(file != null){
        	return file.getOriginalFilename();
        }
        
        return "";
    }
    
    /**
     * 获取附件名称(不包含文件类型)
     * @param file
     * @return
     */
    public static String getSimpleFileName(MultipartFile file){
        if(file != null && !StringUtils.isEmpty(file.getOriginalFilename())){
        	return file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
        }
        
        return "";
    }
    
    /**
     * 生成随机文件名规则:当前时间+4位随机数(+源文件类型,可选)
     * @param file
     * @param cascadeType:是否级联文章类型
     * @return
     */
    public static String formatFileName(MultipartFile file,boolean cascadeType){
    	if(file != null){
    		if(cascadeType){
    			return DateUtil.getTime() + RandomUtils.randomCompose(4, 10) + "." + getFileType(file);
    		}
    		return DateUtil.getTime() + RandomUtils.randomCompose(4, 10);
    	}

    	return "";
    }
    
    
    /**
     * 获取附件类型
     * @param file
     * @return
     */
    public static String getFileType(MultipartFile file){
    	if(file != null){
    		if(!StringUtils.isEmpty(file.getContentType())){
    			return file.getContentType().substring(file.getContentType().indexOf("/")+1,file.getContentType().length());
    		}
        	return "";
        }
        
        return "";
    }
    
    /**
     * 附件大小转换
     * @param file
     * @return
     */
    public static Map<String,Object> formatFileSize(MultipartFile file){
    	long size = file.getSize();
    	Map<String,Object> sizeInfo = new HashMap<String,Object>();
    	DecimalFormat df = new DecimalFormat("0.00");
    	
    	if(size < 1024){
    		sizeInfo.put("size", size);
    		sizeInfo.put("unit", "b");
    	}else if(size/1024 < 1024){
    		sizeInfo.put("size", df.format((double)size/1024));
    		sizeInfo.put("unit", "kb");
    	}else if(size/1024/1024 < 1024){
    		
    		sizeInfo.put("size", df.format((double)size/1024/1024));
    		sizeInfo.put("unit", "mb");
    	}else if(size/1024/1024/1024 < 1024){
    		sizeInfo.put("size", df.format((double)size/1024/1024/1024));
    		sizeInfo.put("unit", "gb");
    	}else{
    		sizeInfo.put("size", df.format((double)size/1024/1024/1024/1024));
    		sizeInfo.put("unit", "tb");
    	}	
    	
    	return sizeInfo;
    }

}
