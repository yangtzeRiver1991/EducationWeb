package com.edu.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edu.dao.AttachmentDao;
import com.edu.entity.Attachment;
import com.edu.service.AttachmentService;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService{

	@Resource 
	private AttachmentDao attachmentDao;
	
	@Override
	@CacheEvict(value="findAttachmentList",key="#attachment.documentId")
	public Attachment insertAttachment(Attachment attachment) {
		// TODO Auto-generated method stub
		return attachmentDao.insertAttachment(attachment);
	}

	@Override
	@Cacheable(value="findAttachmentList",key="#documentId")
	public List<Attachment> findAttachmentList(Integer documentId) {
		// TODO Auto-generated method stub
		return attachmentDao.findAttachmentList(documentId);
	}

	@Override
	@Caching(evict = {@CacheEvict(value="findAttachmentList",key="#documentId"),@CacheEvict(value = "findAttachment",key="#code") }) 
	public Integer deleteAttachment(Integer attachmentId,Integer documentId,String code) {
		// TODO Auto-generated method stub
		return attachmentDao.deleteAttachment(attachmentId);
	}

	@Override
	@Cacheable(value="findAttachment",key="#code")
	public Attachment findAttachment(String code) {
		// TODO Auto-generated method stub
		return attachmentDao.findAttachment(code);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Caching(evict = {@CacheEvict(value="findAttachmentList",key="#attachment.documentId"),@CacheEvict(value = "findAttachment",key="#attachment.code") }) 
	public ResponseEntity downloadAttachment(Attachment attachment,String path,HttpHeaders httpHeaders) throws IOException {
		// TODO Auto-generated method stub
    	attachmentDao.updateDownloadCount(attachment.getCode());

		File file = new File(path+"/"+attachment.getSaveUrl());
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", attachment.getName());
		
		return new ResponseEntity(FileUtils.readFileToByteArray(file), headers,HttpStatus.CREATED);
	}

}
