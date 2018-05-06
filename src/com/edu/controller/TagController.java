package com.edu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.Msg;
import com.edu.entity.PageResult;
import com.edu.entity.Tag;
import com.edu.service.TagService;
import com.edu.util.ArrayUtil;
import com.edu.util.DateUtil;
import com.edu.util.StringUtils;

@Controller
public class TagController {
	private Logger logger = Logger.getLogger(TagController.class);
	
	@Resource
    private TagService tagService;
	
	@RequestMapping(value = "admin/tags")  
    @ResponseBody
    public PageResult<Tag> tags(HttpServletRequest request,@ModelAttribute Tag tag,String beginTime,String endTime,Integer offset,Integer limit) {
    	try{
    		PageResult<Tag> pageResult = tagService.findTags(tag,beginTime,endTime,offset,limit);
    		
    		logger.info("后台加载Tags成功");
    		return pageResult;
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台加载Tags失败,原因为:"+e.getMessage());

            PageResult<Tag> pageResult = new PageResult<Tag>(null,0,1);
    		
    		return pageResult;
    	}
    }
	
	@RequestMapping(value = "admin/addTag")  
    @ResponseBody
    public Msg addTag(HttpServletRequest request,String tagCode,Integer tagLevel,String tagOneLevelCode) {
    	try{
    		Tag tag = new Tag();
    		tag.setCode(tagCode);
    		tag.setLevel(tagLevel);
    		tag.setCreateTime(DateUtil.getTimeTwo());
    		if(!StringUtils.isEmpty(tagOneLevelCode)){
    			tag.setOneLevelCode(tagOneLevelCode);
    		}
    		
    		if(tagService.insertTag(tag) != null){
    			logger.info("后台新增Tag成功");
        		return new Msg(0,"后台新增Tag成功");
    		}
    		
    		logger.info("后台新增Tag失败");
    		return new Msg(1,"后台新增Tag失败");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台新增Tag失败,原因为:"+e.getMessage());

    		return new Msg(1,"后台新增Tag失败");
    	}
    }
	
	
	@RequestMapping(value = "admin/deleteTag")  
    @ResponseBody
    public Msg deleteTag(HttpServletRequest request,String tagIdArray) {
		if(StringUtils.isEmpty(tagIdArray)){
    		return new Msg(1,"操作失败");
    	}
    	
    	try{
    		if(tagIdArray.contains(",")){
    			Integer[] tagsIds = ArrayUtil.convertToIntegerArray(tagIdArray.split(","));
    			
    			tagService.deleteTag(tagsIds);
    		}else{
    			tagService.deleteTag(Integer.parseInt(tagIdArray));
    		}
    		
            return new Msg(0,"操作成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("后台删除标签失败,原因为:"+e.getMessage());
    		
    		return new Msg(1,"操作失败");
    	}
    }
}
