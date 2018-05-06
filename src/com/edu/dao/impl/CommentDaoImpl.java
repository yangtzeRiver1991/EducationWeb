package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.edu.dao.CommentDao;
import com.edu.entity.Comment;
import com.edu.util.StringUtils;

@Repository("commentDao") 
public class CommentDaoImpl extends HibernateDaoSupport implements CommentDao{
	private Logger logger = Logger.getLogger(CommentDaoImpl.class);

	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Comment insertComment(Comment comment) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().save(comment);
			
			logger.info("add comment success,and the id="+comment.getId());
			return comment;
		}catch(Exception e){
			logger.error("add comment fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findCommentList(Comment comment, Integer offset, Integer limit,String orderBy) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("from Comment where 1=1");
			
			if(comment.getId() != null){
				hql.append(" and id= :id");
			}
			if(comment.getDocmentId() != null){
				hql.append(" and docmentId= :docmentId");
			}
			if(comment.getStatus() != null){
				hql.append(" and status = :status");
			}
			if(!StringUtils.isEmpty(orderBy)){
				hql.append(" order by commentDate ")
				   .append(orderBy);
			}
			

			Query query = this.getSession().createQuery(hql.toString());
			if(comment.getId() != null){
				query.setInteger("id", comment.getId());
			}
			if(comment.getDocmentId() != null){
				query.setInteger("docmentId", comment.getDocmentId());
			}
			if(comment.getStatus() != null){
				query.setInteger("status", comment.getStatus());
			}

			
			List<Comment> commentList;
			
			if(offset!=null && limit!=null){
				commentList = (List<Comment>) query.setFirstResult(offset)
							                     .setMaxResults(limit)
							                     .list();
			}else{
				commentList = (List<Comment>) query.list();
			}
			
		    logger.info("find commentList success");
		    
			return commentList;
		}catch(Exception e){
			logger.error("find commentList fail:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer countCommentNum(Comment comment) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("select count(*) from Comment where 1=1");
			
	        if(comment.getId() != null){
				hql.append(" and id= :id");
			}
			if(comment.getDocmentId() != null){
				hql.append(" and docmentId= :docmentId");
			}
			if(comment.getStatus() != null){
				hql.append(" and status = :status");
			}

			Query query = this.getSession().createQuery(hql.toString());
			if(comment.getId() != null){
				query.setInteger("id", comment.getId());
			}
			if(comment.getDocmentId() != null){
				query.setInteger("docmentId", comment.getDocmentId());
			}
			if(comment.getStatus() != null){
				query.setInteger("status", comment.getStatus());
			}

			
		    int i = 0;
			
			i = ((Long)query.uniqueResult()).intValue();
			logger.info("count Comment success");
		    
		    return i;
		}catch(Exception e){
			logger.error("count Comment fail:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer updateCommentStatus(Integer commentId, Integer operation) {
		// TODO Auto-generated method stub
		try{
            String hql = "update Comment set status= :status where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("status", operation);
            query.setInteger("id", commentId);
            
            logger.info("updateCommentStatus success,and the id="+commentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateCommentStatus fail,and the id="+commentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}

	@Override
	public Integer updateCommentStatus(Integer[] commentIds, Integer operation) {
		// TODO Auto-generated method stub
		try{
            String hql = "update Comment set status= :status where id in (:commentIds)";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("status", operation);
            query.setParameterList("commentIds", commentIds);
            
            logger.info("batch-updateCommentStatus success");
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("batch-updateCommentStatus fail,and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}

	@Override
	public Integer updateCommentAgreeNum(Integer commentId) {
		// TODO Auto-generated method stub
		try{
            String hql = "update Comment set agreeNum=agreeNum+1 where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("id", commentId);
            
            logger.info("updateCommentAgreeNum success,and the id="+commentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateCommentAgreeNum fail,and the id="+commentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}

	@Override
	public Integer updateCommentAgainstNum(Integer commentId) {
		// TODO Auto-generated method stub
		try{
            String hql = "update Comment set againstNum=againstNum+1 where id= :id";
            
            Query query = this.getSession().createQuery(hql);
            query.setInteger("id", commentId);
            
            logger.info("updateCommentAgainstNum success,and the id="+commentId);
            return query.executeUpdate();
        }catch(Exception e){
            logger.error("updateCommentAgainstNum fail,and the id="+commentId+",and the error is:"+e.getMessage());
            e.printStackTrace();
            
            return null;
        }
	}

}
