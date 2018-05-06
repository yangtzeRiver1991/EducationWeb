package com.edu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.edu.dao.CommentDao;
import com.edu.dao.DocumentDao;
import com.edu.entity.Comment;
import com.edu.entity.PageResult;
import com.edu.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService{

	@Resource 
	private CommentDao commentDao;
	
	@Resource 
    private DocumentDao documentDao;
	
	@Override
	@CacheEvict(value="findCommentList",allEntries=true)
	public Comment insertComment(Comment comment) {
		// TODO Auto-generated method stub
	    documentDao.updateCommentNum(comment.getDocmentId());
		return commentDao.insertComment(comment);
	}
	
	@Override
	@Cacheable(value="findComment",key="#commentId.toString()")
	public Comment findComment(Integer commentId){
		Comment comment = new Comment();
		comment.setId(commentId);
		
		List<Comment> commentList = commentDao.findCommentList(comment, null, null,null);
		
		if(commentList!=null && commentList.size()>0){
			return commentList.get(0);
		}
		
		return null;
	}

	@Override
	@Cacheable(value="findCommentList",key="#docId.toString().concat(#orderBy).concat(#pageSize.toString()).concat(#nowPageNum.toString())")
	public PageResult<Comment> findCommentList(Integer docId, Integer nowPageNum, Integer pageSize,String orderBy) {
		// TODO Auto-generated method stub
		Comment comment = new Comment();
		comment.setDocmentId(docId);
		
		List<Comment> commentList = commentDao.findCommentList(comment, (nowPageNum-1)*pageSize, pageSize,orderBy);
		Integer commentNum = commentDao.countCommentNum(comment);
		
		PageResult<Comment> pageResult = new PageResult<Comment>(commentList, nowPageNum, pageSize,commentNum, 0,orderBy);
		
		return pageResult;
	}

	@Override
	@Caching(evict = {@CacheEvict(value="findCommentList",allEntries=true),@CacheEvict(value = "findComment",key="#commentId.toString()")}) 
	public Integer updateCommentStatus(Integer commentId, Integer operation) {
		// TODO Auto-generated method stub
		return commentDao.updateCommentStatus(commentId, operation);
	}

	@Override
	@Caching(evict = {@CacheEvict(value="findCommentList",allEntries=true),@CacheEvict(value = "findComment",allEntries=true)}) 
	public Integer updateCommentStatus(Integer[] commentIds, Integer operation) {
		// TODO Auto-generated method stub
		return commentDao.updateCommentStatus(commentIds, operation);
	}

	@Override
	@CacheEvict(value="findCommentList",allEntries=true)
	public Integer updateCommentAgreeNum(Integer commentId) {
		// TODO Auto-generated method stub
		return commentDao.updateCommentAgreeNum(commentId);
	}

	@Override
	@CacheEvict(value="findCommentList",allEntries=true)
	public Integer updateCommentAgainstNum(Integer commentId) {
		// TODO Auto-generated method stub
		return commentDao.updateCommentAgainstNum(commentId);
	}

}
