package com.edu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.dao.TagDao;
import com.edu.entity.PageResult;
import com.edu.entity.Tag;
import com.edu.service.TagService;

@Service("tagService")
public class TagServiceImpl implements TagService{

	@Resource 
	private TagDao tagtDao;

	@Override
	public Tag insertTag(Tag tag) {
		// TODO Auto-generated method stub
		return tagtDao.insertTag(tag);
	}

	@Override
	public PageResult<Tag> findTags(Tag tag,String beginTime,String endTime, Integer offset,Integer limit) {
		// TODO Auto-generated method stub
		List<Tag> tags = tagtDao.findTags(tag, beginTime,endTime, offset, limit);
		Integer tagNum = tagtDao.countTagNum(tag, beginTime,endTime);

		PageResult<Tag> pageResult = new PageResult<Tag>(tags,tagNum, 0);
		                                                                                                                                                                                                                                   
		return pageResult;
	}

	@Override
	public Integer deleteTag(Integer[] ids) {
		// TODO Auto-generated method stub
		return tagtDao.deleteTag(ids);
	}

	@Override
	public Integer deleteTag(Integer id) {
		// TODO Auto-generated method stub
		return tagtDao.deleteTag(id);
	}
	
	

}
