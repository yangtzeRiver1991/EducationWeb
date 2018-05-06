package com.edu.service;

import com.edu.entity.Comment;
import com.edu.entity.PageResult;

public interface CommentService {

	 public Comment insertComment(Comment comment);
	 
	 public Comment findComment(Integer commentId);
		
	 public PageResult<Comment> findCommentList(Integer docId,Integer nowPageNum,Integer pageSize,String orderBy);
	 
	 public Integer updateCommentStatus(Integer commentId,Integer operation);
	 
	 public Integer updateCommentStatus(Integer[] commentIds,Integer operation);
	 
	 public Integer updateCommentAgreeNum(Integer commentId);
		
	 public Integer updateCommentAgainstNum(Integer commentId);
	 
}
