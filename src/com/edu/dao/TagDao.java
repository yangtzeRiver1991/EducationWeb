package com.edu.dao;

import java.util.List;

import com.edu.entity.Tag;

public interface TagDao {

    public Tag insertTag(Tag tag);
	
	public List<Tag> findTags(Tag tag,String beginTime,String endTime,Integer offset, Integer limit);
	
	public Integer countTagNum(Tag tag,String beginTime,String endTime);
	 
	public Integer deleteTag(Integer[] ids);
	
	public Integer deleteTag(Integer id);
}
