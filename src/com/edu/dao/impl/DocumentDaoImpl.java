package com.edu.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.edu.dao.DocumentDao;
import com.edu.entity.Document;
import com.edu.util.StringUtils;

@Repository("documentDao") 
public class DocumentDaoImpl extends HibernateDaoSupport implements DocumentDao{
	private Logger logger = Logger.getLogger(DocumentDaoImpl.class);

	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Document insertDocument(Document document) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().save(document);
			
			logger.info("add document success,and the id="+document.getId());
			return document;
		}catch(Exception e){
			logger.error("add document fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	@Override
	public Integer updateDocument(Document document) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("update Document set");
			boolean splitMarkNeed = false;
			
			if(document != null){
				if(!StringUtils.isEmpty(document.getTitle())){
					hql.append(" title= :title");
					splitMarkNeed = true;
				}
				if(!StringUtils.isEmpty(document.getTags())){
					if(splitMarkNeed){
						hql.append(",");
					}else{
						splitMarkNeed = true;
					}
					hql.append(" tags= :tags");
				}
				if(!StringUtils.isEmpty(document.getIntro())){
					if(splitMarkNeed){
						hql.append(",");
					}else{
						splitMarkNeed = true;
					}
					hql.append(" intro= :intro");
				}
				if(!StringUtils.isEmpty(document.getContent())){
					if(splitMarkNeed){
						hql.append(",");
					}else{
						splitMarkNeed = true;
					}
					hql.append(" content= :content");
				}
				if(!StringUtils.isEmpty(document.getUploadImgs())){
					if(splitMarkNeed){
						hql.append(",");
					}else{
						splitMarkNeed = true;
					}
					hql.append(" uploadImgs= :uploadImgs");
				}
				
			}
			hql.append(" where id= :id");
			
			Query query = this.getSession().createQuery(hql.toString());
			 
			if(document != null){
				if(!StringUtils.isEmpty(document.getTitle())){
					query.setString("title", document.getTitle());
				}
				if(!StringUtils.isEmpty(document.getTags())){
					query.setString("tags", document.getTags());
				}
				if(!StringUtils.isEmpty(document.getIntro())){
					query.setString("intro", document.getIntro());
				}
				if(!StringUtils.isEmpty(document.getContent())){
					query.setString("content", document.getContent());
				}
				if(!StringUtils.isEmpty(document.getUploadImgs())){
					query.setString("uploadImgs", document.getUploadImgs());
				}
			}
			query.setInteger("id", document.getId());
			
			logger.info("update document success,and the id="+document.getId());
			return query.executeUpdate();
		}catch(Exception e){
			logger.error("update document fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	@Override
    public Integer updateDocumentIsHot(Integer documentId,Integer operation){
		// TODO Auto-generated method stub
        try{
            String hql = "update Document set isHot= :isHot where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("isHot", operation);
            query.setInteger("id", documentId);
            
            logger.info("updateDocumentIsHot success,and the id="+documentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateDocumentIsHot fail,and the id="+documentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}
	
	@Override
	public Integer updateDocumentIsHot(Integer[] documentIds,Integer operation){
		// TODO Auto-generated method stub
        try{
            String hql = "update Document set isHot= :isHot where id in (:documentIds)";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("isHot", operation);
            query.setParameterList("documentIds", documentIds);
            
            
            logger.info("batch-updateDocumentIsHot success");
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("batch-updateDocumentIsHot fail,and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}
	
	@Override
	public Integer updateDocumentIsTop(Integer[] documentIds,Integer operation){
		// TODO Auto-generated method stub
        try{
            String hql = "update Document set isTop= :isTop where id in (:documentIds)";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("isTop", operation);
            query.setParameterList("documentIds", documentIds);
            
            logger.info("batch-updateDocumentIsTop success");
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("batch-updateDocumentIsTop fail,and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}
    
	@Override
    public Integer updateDocumentIsTop(Integer documentId,Integer operation,String carouselImg){
    	// TODO Auto-generated method stub
        try{
            String hql = "update Document set isTop= :isTop,carouselImg= :carouselImg where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("isTop", operation);
            query.setInteger("id", documentId);
            query.setString("carouselImg", carouselImg);
            
            logger.info("updateDocumentIsTop success,and the id="+documentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateDocumentIsTop fail,and the id="+documentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
    }
	
	@Override
	public Integer updateDocumentStatus(Integer[] documentIds,Integer operation){
		// TODO Auto-generated method stub
        try{
            String hql = "update Document set status= :status where id in (:documentIds)";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("status", operation);
            query.setParameterList("documentIds", documentIds);
            
            logger.info("batch-updateDocumentStatus success");
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("batch-updateDocumentStatus fail,and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}
	
	@Override
	public Integer updateDocumentStatus(Integer documentId,Integer operation){
		// TODO Auto-generated method stub
		try{
            String hql = "update Document set status= :status where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("status", operation);
            query.setInteger("id", documentId);
            
            logger.info("updateDocumentStatus success,and the id="+documentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateDocumentStatus fail,and the id="+documentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}
    
	
	@Override
	public Integer updateLookNum(Integer documentId){
	    // TODO Auto-generated method stub
        try{
            String hql = "update Document set lookNum=lookNum+1 where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("id", documentId);
            
            logger.info("updateLookNum success,and the id="+documentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateLookNum fail,and the id="+documentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}
	
	@Override
    public Integer updateCommentNum(Integer documentId){
        // TODO Auto-generated method stub
        try{
            String hql = "update Document set commentNum=commentNum+1 where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("id", documentId);
            
            logger.info("commentNum success,and the id="+documentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("commentNum fail,and the id="+documentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
    }
	
	@SuppressWarnings("unchecked")
	public Document findDocumentByTitle(String title){
		// TODO Auto-generated method stub
		try{
			String hql = "from Document where title= :title and status=0";
			Query query = this.getSession().createQuery(hql.toString());

			query.setString("title", title);

			List<Document> documentList = (List<Document>) query.list();
			
			logger.info("find findDocumentByTitle success");
			if(documentList!=null && documentList.size()>0){
				return documentList.get(0);
			}
		    
			return null;
		}catch(Exception e){
			logger.error("find findDocumentByTitle fail,and the title=:"+title+".The error is:"+e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentList(Document document, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("from Document where 1=1");
	        
	        if(document != null){
	        	if(document.getId()!=null){
	                 hql.append(" and id = :id");
	            }
	     		if(!StringUtils.isEmpty(document.getTitle())){
	     			hql.append(" and title like :title");
	     		}
	     		if(!StringUtils.isEmpty(document.getTags())){
	     			hql.append(" and tags like :tags");
	     		}
	     		if(document.getStatus()!=null){
	     			hql.append(" and status = :status");
	     		}
	        }
			

			hql.append(" order by createTime desc ");
			
			Query query = this.getSession().createQuery(hql.toString());
			
			if(document != null){
				if(document.getId()!=null){
				    query.setInteger("id", document.getId());
				}
				if(!StringUtils.isEmpty(document.getTitle())){
					query.setString("title", "%"+document.getTitle()+"%");
				}
				if(!StringUtils.isEmpty(document.getTags())){
					query.setString("tags", "%"+document.getTags()+",%");
				}
				if(document.getStatus()!=null){
					query.setInteger("status", document.getStatus());
				}
			}
			
			List<Document> documentList;
			
			if(offset!=null && limit!=null){
				documentList = (List<Document>) query.setFirstResult(offset)
							                     .setMaxResults(limit)
							                     .list();
			}else{
				documentList = (List<Document>) query.list();
			}
			
		    logger.info("find documentList success");
		    
			return documentList;
		}catch(Exception e){
			logger.error("find documentList fail:"+e.getMessage());
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocumentList(Document document,String createTime,String endTime,Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("from Document where 1=1");
	        
	        if(document != null){
	     		if(!StringUtils.isEmpty(document.getTitle())){
	     			hql.append(" and title like :title");
	     		}
	     		if(!StringUtils.isEmpty(document.getTags())){
	     			hql.append(" and tags like :tags");
	     		}
	     		if(document.getStatus()!=null){
	     			hql.append(" and status = :status");
	     		}
	     		if(document.getIsHot()!=null){
	     			hql.append(" and isHot = :isHot");
	     		}
	     		if(document.getIsTop()!=null){
	     			hql.append(" and isTop = :isTop");
	     		}
	        }
			
	        if(!StringUtils.isEmpty(createTime)){
     			hql.append(" and createTime >= :createTime");
     		}
     		if(!StringUtils.isEmpty(endTime)){
     			hql.append(" and createTime <= :endTime");
     		}

			hql.append(" order by createTime desc ");
			
			Query query = this.getSession().createQuery(hql.toString());
			
			if(document != null){
				if(!StringUtils.isEmpty(document.getTitle())){
					query.setString("title", "%"+document.getTitle()+"%");
				}
				if(!StringUtils.isEmpty(document.getTags())){
					query.setString("tags", "%"+document.getTags()+",%");
				}
				if(document.getStatus()!=null){
					query.setInteger("status", document.getStatus());
				}
				if(document.getIsHot()!=null){
					query.setInteger("isHot", document.getIsHot());
	     		}
	     		if(document.getIsTop()!=null){
	     			query.setInteger("isTop", document.getIsTop());
	     		}
			}
			
			if(!StringUtils.isEmpty(createTime)){
     			query.setString("createTime", createTime);
     		}
     		if(!StringUtils.isEmpty(endTime)){
     			query.setString("endTime", endTime);
     		}
			
			List<Document> documentList;
			
			if(offset!=null && limit!=null){
				documentList = (List<Document>) query.setFirstResult(offset)
							                     .setMaxResults(limit)
							                     .list();
			}else{
				documentList = (List<Document>) query.list();
			}
			
		    logger.info("find documentList success");
		    
			return documentList;
		}catch(Exception e){
			logger.error("find documentList fail:"+e.getMessage());
			return null;
		}
	}
	
	
	
	@Override
	public Integer countDocumentNum(Document document,String createTime,String endTime) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("select count(*) from Document where 1=1");
	        
	        if(document != null){
	    		if(!StringUtils.isEmpty(document.getTitle())){
	    			hql.append(" and title like :title");
	    		}
	    		if(!StringUtils.isEmpty(document.getTags())){
	    			hql.append(" and tags like :tags");
	    		}
	    		if(document.getStatus()!=null){
	    			hql.append(" and status = :status");
	    		}
	    		if(document.getIsHot()!=null){
	     			hql.append(" and isHot = :isHot");
	     		}
	     		if(document.getIsTop()!=null){
	     			hql.append(" and isTop = :isTop");
	     		}
	        }
	        
	        if(!StringUtils.isEmpty(createTime)){
     			hql.append(" and createTime >= :createTime");
     		}
     		if(!StringUtils.isEmpty(endTime)){
     			hql.append(" and createTime <= :endTime");
     		}
			

			Query query = this.getSession().createQuery(hql.toString());
			
			if(document != null){
				if(!StringUtils.isEmpty(document.getTitle())){
					query.setString("title", "%"+document.getTitle()+"%");
				}
				if(!StringUtils.isEmpty(document.getTags())){
					query.setString("tags", "%"+document.getTags()+",%");
				}
				if(document.getStatus()!=null){
					query.setInteger("status", document.getStatus());
				}
				if(document.getIsHot()!=null){
					query.setInteger("isHot", document.getIsHot());
	     		}
	     		if(document.getIsTop()!=null){
	     			query.setInteger("isTop", document.getIsTop());
	     		}
			}
			
			if(!StringUtils.isEmpty(createTime)){
     			query.setString("createTime", createTime);
     		}
     		if(!StringUtils.isEmpty(endTime)){
     			query.setString("endTime", endTime);
     		}
			
		    int i = 0;
		    
			i = ((Long)query.uniqueResult()).intValue();
			logger.info("count Document success");
		    
		    return i;
		}catch(Exception e){
			logger.error("count Document fail:"+e.getMessage());
			return null;
		}
	}

	
	@Override
	public Integer countDocumentNum(Document document) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("select count(*) from Document where 1=1");
	        
	        if(document != null){
	        	if(document.getId()!=null){
	                hql.append(" and id = :id");
	            }
	    		if(!StringUtils.isEmpty(document.getTitle())){
	    			hql.append(" and title like :title");
	    		}
	    		if(!StringUtils.isEmpty(document.getTags())){
	    			hql.append(" and tags like :tags");
	    		}
	    		if(document.getStatus()!=null){
	    			hql.append(" and status = :status");
	    		}
	        }
			

			Query query = this.getSession().createQuery(hql.toString());
			
			if(document != null){
				if(document.getId()!=null){
				    query.setInteger("id", document.getId());
				}
				if(!StringUtils.isEmpty(document.getTitle())){
					query.setString("title", "%"+document.getTitle()+"%");
				}
				if(!StringUtils.isEmpty(document.getTags())){
					query.setString("tags", "%"+document.getTags()+",%");
				}
				if(document.getStatus()!=null){
					query.setInteger("status", document.getStatus());
				}
			}
			
		    int i = 0;
		    
			i = ((Long)query.uniqueResult()).intValue();
			logger.info("count Document success");
		    
		    return i;
		}catch(Exception e){
			logger.error("count Document fail:"+e.getMessage());
			return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
    public List<String> findTitle(String  queryTitle){
	    try{
	        String sql = "select title from t_document where status=0 and title like :title order by looknum desc,commentnum desc,createtime desc limit 10";
	        Query query = this.getSession().createSQLQuery(sql);
	        query.setString("title", "%"+queryTitle+"%");
	        
	        
	        List<String> list = (List<String>)query.list();
	        
	        logger.info("find findTitle success");
	        return list;  
        }catch(Exception e){
            logger.error("find findTitle fail:"+e.getMessage());
            return null;
        }
	}

	
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> findBeforeDocument(Integer documentId,String tags) {
        // TODO Auto-generated method stub
        try{
            String sql = "select id,title from t_document where id< :id and tags like :tags order by createtime desc limit 1";
            Query query = this.getSession().createSQLQuery(sql);
            query.setInteger("id", documentId);
            query.setString("tags", "%"+tags+"%");
            
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            
            List<Map<String,Object>> list = query.list();
            logger.info("find findBeforeDocument success");
            
            if(list != null && list.size()>0){
            	return list.get(0);            
            }
            
            return null;
        }catch(Exception e){
            logger.error("find findBeforeDocument fail:"+e.getMessage());
            return null;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> findNextDocument(Integer documentId,String tags) {
        // TODO Auto-generated method stub
        try{
            String sql = "select id,title from t_document where id> :id and tags like :tags order by createtime asc limit 1";
            Query query = this.getSession().createSQLQuery(sql);
            query.setInteger("id", documentId);
            query.setString("tags", "%"+tags+"%");
            
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            
            List<Map<String,Object>> list = query.list();
            logger.info("find nextDocument success");
            
            if(list != null && list.size()>0){
            	return list.get(0);            
            }
            
            return null;  
        }catch(Exception e){
            logger.error("find nextDocument fail:"+e.getMessage());
            return null;
        }
    }

}
