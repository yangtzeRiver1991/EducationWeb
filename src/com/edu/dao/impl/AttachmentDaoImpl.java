package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.edu.dao.AttachmentDao;
import com.edu.entity.Attachment;

@Repository("attachmentDao") 
public class AttachmentDaoImpl extends HibernateDaoSupport implements AttachmentDao{
	private Logger logger = Logger.getLogger(AttachmentDaoImpl.class);

	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public Attachment insertAttachment(Attachment attachment) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().save(attachment);
			
			logger.info("add attachment success,and the attachmentId="+attachment.getId());
			return attachment;
		}catch(Exception e){
			logger.error("add attachment fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> findAttachmentList(Integer documentId) {
		// TODO Auto-generated method stub
		try{
			String hql = "from Attachment where documentId= :documentId order by uploadTime asc";
			
			Query query = this.getSession().createQuery(hql);
			query.setInteger("documentId", documentId);
			
			List<Attachment> attachmentList = (List<Attachment>)query.list();
			
		    logger.info("find attachmentList success,and the documentId="+documentId);
			return attachmentList;
		}catch(Exception e){
			logger.error("find attachmentList fail,and the documentId="+documentId+".the error is:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer deleteAttachment(Integer attachmentId) {
		// TODO Auto-generated method stub
		try{
			String hql = "delete from Attachment where id= :attachmentId";

			Query query = this.getSession().createQuery(hql);
			query.setInteger("attachmentId", attachmentId);
			
		    logger.info("delete attachment success,and the attachmentId="+attachmentId);
			return query.executeUpdate();
		}catch(Exception e){
			logger.error("delete attachment fail,and the attachmentId="+attachmentId+".the error is:"+e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attachment findAttachment(String code) {
		// TODO Auto-generated method stub
		try{
	        String hql = "from Attachment where code= :code";
			
			Query query = this.getSession().createQuery(hql);
			query.setString("code", code);
			
			List<Attachment> attachmentList = (List<Attachment>)query.list();
			if(attachmentList!=null && attachmentList.size()>0){
				logger.info("find attachment success,and the code="+code);
				return attachmentList.get(0);
			}
			
		    logger.info("find attachment fail,and the code="+code);
			return null;
		}catch(Exception e){
			logger.error("find attachment fail,and the code="+code+".the error is:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer updateDownloadCount(String code) {
		// TODO Auto-generated method stub
		try{
	        String hql = "update Attachment set downloadCount=downloadCount+1 where code= :code";
			
            Query query = this.getSession().createQuery(hql);
            query.setString("code", code);
            
            logger.info("updateDownloadCount success,and the code="+code);
            return query.executeUpdate();
		}catch(Exception e){
			logger.error("updateDownloadCount fail,and the code="+code+".the error is:"+e.getMessage());
			return null;
		}
	}

}
