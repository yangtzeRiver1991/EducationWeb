package com.edu.service;

import java.util.List;
import java.util.Map;

import com.edu.entity.Document;
import com.edu.entity.PageResult;

public interface DocumentService {

	 public Document insertDocument(Document document);
	 
	 public Integer updateDocument(Document document);
	 
	 public Document findDocument(String title);
	 
	 public Document findDocument(Integer documentId,Integer status);
	 
	 public List<Document> findHotDocumentList();
	 
	 public List<Document> findTopDocumentList();
	 
	 public List<Document> findDocumentByTag(String tags);
		
	 public PageResult<Document> findDocumentList(Document document,Integer nowPageNum,Integer pageSize);
	 
	 public PageResult<Document> findDocumentList(Document document,String createTime,String endTime,Integer offset,Integer limit);
	 
	 public Integer updateLookNum(Integer documentId);
	 
	 public Integer updateCommentNum(Integer documentId);
	 
	 public Integer updateDocumentIsHot(Integer documentId,Integer operation);
	 
	 public Integer updateDocumentIsHot(Integer[] documentIds,Integer operation);
	 
	 public Integer updateDocumentIsTop(Integer documentId,Integer operation,String carouselImg);
	 
	 public Integer updateDocumentIsTop(Integer[] documentIds,Integer operation);
	 
     public Integer updateDocumentStatus(Integer documentId,Integer operation);
	 
	 public Integer updateDocumentStatus(Integer[] documentIds,Integer operation);
	 
	 public List<String> findTitle(String  queryTitle);
	 
	 public Map<String,Object> findUpDownDocument(Integer documentId,String tags);
	 
}
