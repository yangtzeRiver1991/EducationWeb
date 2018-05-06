package com.edu.dao;

import java.util.List;

import com.edu.entity.Comment;

public interface CommentDao {

    public Comment insertComment(Comment comment);
    
	public List<Comment> findCommentList(Comment comment,Integer offset,Integer limit,String orderBy);
	
	public Integer countCommentNum(Comment comment);
	
	public Integer updateCommentStatus(Integer commentId,Integer operation);
	 
	public Integer updateCommentStatus(Integer[] commentIds,Integer operation);
	
	public Integer updateCommentAgreeNum(Integer commentId);
	
	public Integer updateCommentAgainstNum(Integer commentId);
}
