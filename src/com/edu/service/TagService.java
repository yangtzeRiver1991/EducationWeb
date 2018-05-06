package com.edu.service;

import com.edu.entity.PageResult;
import com.edu.entity.Tag;

public interface TagService {

    public Tag insertTag(Tag tag);
	
	public PageResult<Tag> findTags(Tag tag,String beginTime,String endTime,Integer offset, Integer limit);
	 
	public Integer deleteTag(Integer[] ids);
	
	public Integer deleteTag(Integer id);
	 
}
