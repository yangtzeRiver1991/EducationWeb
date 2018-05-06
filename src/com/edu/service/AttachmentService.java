package com.edu.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.edu.entity.Attachment;

public interface AttachmentService {

    public Attachment insertAttachment(Attachment attachment);
	
	public List<Attachment> findAttachmentList(Integer documentId);
	 
	public Integer deleteAttachment(Integer attachmentId,Integer documentId,String code);
	
	public Attachment findAttachment(String code);
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity downloadAttachment(Attachment attachment,String path,HttpHeaders httpHeaders) throws IOException;
	 
}
