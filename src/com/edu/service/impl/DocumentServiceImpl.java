package com.edu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.edu.dao.DocumentDao;
import com.edu.entity.Document;
import com.edu.entity.PageResult;
import com.edu.service.CommentService;
import com.edu.service.DocumentService;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService{

	@Resource 
	private DocumentDao documentDao;
	
	@Resource
	private CommentService commentService;
	
	@Override
	public Document insertDocument(Document document) {
		// TODO Auto-generated method stub
		return documentDao.insertDocument(document);
	}
	
	@Override
	public Integer updateDocument(Document document) {
		// TODO Auto-generated method stub
		return documentDao.updateDocument(document);
	}
	
	@Override
	@Cacheable(value="findHotDocumentList")
	public List<Document> findHotDocumentList(){
		Document document = new Document();
		document.setIsHot(1);
		document.setStatus(0);
		
		return documentDao.findDocumentList(document, null, null, null, null);
	}
	
	@Override
	public List<Document> findDocumentByTag(String tags){
		Document document = new Document();
		document.setTags(tags);
		document.setStatus(0);
		
		return documentDao.findDocumentList(document, null, null, null, null);
	}
	
	@Override
	@Cacheable(value="findTopDocumentList")
	public List<Document> findTopDocumentList(){
		Document document = new Document();
		document.setIsTop(1);
		document.setStatus(0);
		
		return documentDao.findDocumentList(document, null, null, null, null);
	}

	@Override
	@Cacheable(value="findDocumentList",key="#nowPageNum.toString().concat(#document.title).concat(#pageSize.toString()).concat(#document.tags)")
	public PageResult<Document> findDocumentList(Document document, Integer nowPageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		List<Document> documentList = documentDao.findDocumentList(document,  (nowPageNum-1)*pageSize, pageSize);
		Integer documentNum = documentDao.countDocumentNum(document);
		
		PageResult<Document> pageResult = new PageResult<Document>(documentList, nowPageNum, pageSize,documentNum, 0);
		
		return pageResult;
	}
	
	@Override
	public PageResult<Document> findDocumentList(Document document,String createTime,String endTime,Integer offset,Integer limit) {
		// TODO Auto-generated method stub
		List<Document> documentList = documentDao.findDocumentList(document,createTime,endTime,offset, limit);
		Integer documentNum = documentDao.countDocumentNum(document,createTime,endTime);

		PageResult<Document> pageResult = new PageResult<Document>(documentList,documentNum,0);
		
		return pageResult;
	}

    @Override
    @Cacheable(value="findDocumentByTitle",key="#title")
    public Document findDocument(String title) {
        // TODO Auto-generated method stub
        return documentDao.findDocumentByTitle(title);
    }

    @Override
    public Integer updateLookNum(Integer documentId) {
        // TODO Auto-generated method stub
        return documentDao.updateLookNum(documentId);
    }

    @Override
    @Cacheable(value="findDocumentByID",key="#documentId.toString()",condition="#status!=null")
    public Document findDocument(Integer documentId,Integer status) {
        // TODO Auto-generated method stub
        Document temp_document = new Document();
        temp_document.setId(documentId);
        if(status != null){
        	temp_document.setStatus(status);
        }
        
        List<Document> documentList = documentDao.findDocumentList(temp_document, null, null);

        if(documentList!=null && documentList.size()>0){
            return documentList.get(0);
        }
        
        return null;
    }

    @Override
    public Integer updateCommentNum(Integer documentId) {
        // TODO Auto-generated method stub
        return documentDao.updateCommentNum(documentId);
    }

    @Override
    public List<String> findTitle(String queryTitle) {
        // TODO Auto-generated method stub
        return documentDao.findTitle(queryTitle);
    }

    @Override
    @Cacheable(value="findUpDownDocument",key="#documentId.toString()")
    public Map<String, Object> findUpDownDocument(Integer documentId, String tags) {
        // TODO Auto-generated method stub
        Map<String, Object> pageDocument = new HashMap<String, Object>();
        pageDocument.put("before", documentDao.findBeforeDocument(documentId, tags));
        pageDocument.put("next", documentDao.findNextDocument(documentId, tags));
        
        return pageDocument;
    }

	@Override
	public Integer updateDocumentIsHot(Integer documentId, Integer operation) {
		// TODO Auto-generated method stub
		return documentDao.updateDocumentIsHot(documentId, operation);
	}

	@Override
	public Integer updateDocumentIsTop(Integer documentId, Integer operation,String carouselImg) {
		// TODO Auto-generated method stub
		return documentDao.updateDocumentIsTop(documentId, operation,carouselImg);
	}

	@Override
	public Integer updateDocumentIsHot(Integer[] documentIds, Integer operation) {
		// TODO Auto-generated method stub
		return documentDao.updateDocumentIsHot(documentIds, operation);
	}

	@Override
	public Integer updateDocumentIsTop(Integer[] documentIds, Integer operation) {
		// TODO Auto-generated method stub
		return documentDao.updateDocumentIsTop(documentIds, operation);
	}
	
	@Override
	public Integer updateDocumentStatus(Integer documentId,Integer operation){
		return documentDao.updateDocumentStatus(documentId, operation);
	}
	
	@Override
	public Integer updateDocumentStatus(Integer[] documentIds,Integer operation){
		return documentDao.updateDocumentStatus(documentIds, operation);
	}

}
