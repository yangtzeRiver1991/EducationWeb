package com.edu.dao;

import java.util.List;
import java.util.Map;

import com.edu.entity.Document;

public interface DocumentDao {

    public Document insertDocument(Document document);
    
    public Integer updateDocument(Document document);
    
    public Integer updateLookNum(Integer documentId);
    
    public Integer updateCommentNum(Integer documentId);
    
    public Integer updateDocumentIsHot(Integer documentId,Integer operation);
    
    public Integer updateDocumentIsHot(Integer[] documentIds,Integer operation);
    
    public Integer updateDocumentIsTop(Integer documentId,Integer operation,String carouselImg);
    
    public Integer updateDocumentIsTop(Integer[] documentIds,Integer operation);
    
    public Integer updateDocumentStatus(Integer documentId,Integer operation);
	 
	public Integer updateDocumentStatus(Integer[] documentIds,Integer operation);
	
	public List<Document> findDocumentList(Document document,Integer offset,Integer limit);
	
	public List<Document> findDocumentList(Document document,String createTime,String endTime,Integer offset,Integer limit);
	
	public Document findDocumentByTitle(String title);
	
	public Integer countDocumentNum(Document document);
	
	public Integer countDocumentNum(Document document,String createTime,String endTime);
	
	public List<String> findTitle(String  queryTitle);
	
	public Map<String,Object> findBeforeDocument(Integer documentId,String tags);
	
	public Map<String,Object> findNextDocument(Integer documentId,String tags);
}
