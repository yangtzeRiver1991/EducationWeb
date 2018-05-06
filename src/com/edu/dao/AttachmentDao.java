package com.edu.dao;

import java.util.List;

import com.edu.entity.Attachment;

public interface AttachmentDao {

    public Attachment insertAttachment(Attachment attachment);
	
	public List<Attachment> findAttachmentList(Integer documentId);
	 
	public Integer deleteAttachment(Integer attachmentId);
	
	public Attachment findAttachment(String code);
	
	public Integer updateDownloadCount(String code);
}
