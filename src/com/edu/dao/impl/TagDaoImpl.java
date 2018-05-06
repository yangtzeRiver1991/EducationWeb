package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.edu.dao.TagDao;
import com.edu.entity.Tag;
import com.edu.util.StringUtils;

@Repository("tagDao") 
public class TagDaoImpl extends HibernateDaoSupport implements TagDao{
	private Logger logger = Logger.getLogger(TagDaoImpl.class);

	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public Tag insertTag(Tag tag) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().save(tag);
			
			logger.info("add tag success,and the tag="+tag.getId());
			return tag;
		}catch(Exception e){
			logger.error("add tag fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findTags(Tag tag,String beginTime,String endTime,Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("from Tag where 1=1");
			
			if(tag != null){
				if(!StringUtils.isEmpty(tag.getCode())){
					hql.append(" and code= :code");
				}
				
				if(tag.getLevel()!=null){
					hql.append(" and level= :level");
				}
				
				if(!StringUtils.isEmpty(tag.getOneLevelCode())){
					hql.append(" and oneLevelCode= :oneLevelCode");
				}
				
			}
			
			if(!StringUtils.isEmpty(beginTime)){
				hql.append(" and createTime >= :beginTime");
			}
            if(!StringUtils.isEmpty(endTime)){
            	hql.append(" and createTime <= :endTime");
			}
			
			hql.append(" order by level asc");
			
			Query query = this.getSession().createQuery(hql.toString());
			
			if(tag != null){
				if(!StringUtils.isEmpty(tag.getCode())){
					query.setString("code", tag.getCode());
				}
				
				if(tag.getLevel()!=null){
					query.setInteger("level", tag.getLevel());
				}
				
				if(!StringUtils.isEmpty(tag.getOneLevelCode())){
					query.setString("oneLevelCode", tag.getOneLevelCode());
				}
				
			}
			
			if(!StringUtils.isEmpty(beginTime)){
				query.setString("beginTime", beginTime);
			}
            if(!StringUtils.isEmpty(beginTime)){
            	query.setString("endTime", endTime);
			}
			
			List<Tag> tags;
			
			if(offset!=null && limit!=null){
				tags = (List<Tag>) query.setFirstResult(offset)
							                     .setMaxResults(limit)
							                     .list();
			}else{
				tags = (List<Tag>) query.list();
			}
			
		    logger.info("find tags success");
			return tags;
		}catch(Exception e){
			logger.error("find tags fail.the error is:"+e.getMessage());
			return null;
		}
	}
	
	@Override
	public Integer countTagNum(Tag tag,String beginTime,String endTime){
		try{
			StringBuffer hql = new StringBuffer("select count(*) from Tag where 1=1");
			
			if(tag != null){
				if(!StringUtils.isEmpty(tag.getCode())){
					hql.append(" and code= :code");
				}
				
				if(tag.getLevel()!=null){
					hql.append(" and level= :level");
				}
				
				if(!StringUtils.isEmpty(tag.getOneLevelCode())){
					hql.append(" and oneLevelCode= :oneLevelCode");
				}
				
			}
			
			if(!StringUtils.isEmpty(beginTime)){
				hql.append(" and createTime >= :beginTime");
			}
            if(!StringUtils.isEmpty(endTime)){
            	hql.append(" and createTime <= :endTime");
			}
			
			
			Query query = this.getSession().createQuery(hql.toString());
			
			if(tag != null){
				if(!StringUtils.isEmpty(tag.getCode())){
					query.setString("code", tag.getCode());
				}
				
				if(tag.getLevel()!=null){
					query.setInteger("level", tag.getLevel());
				}
				
				if(!StringUtils.isEmpty(tag.getOneLevelCode())){
					query.setString("oneLevelCode", tag.getOneLevelCode());
				}
				
			}
			
			if(!StringUtils.isEmpty(beginTime)){
				query.setString("beginTime", beginTime);
			}
            if(!StringUtils.isEmpty(beginTime)){
            	query.setString("endTime", endTime);
			}
			
            int i = 0;
		    
			i = ((Long)query.uniqueResult()).intValue();
			logger.info("count Tag success");
		    
		    return i;
			
		}catch(Exception e){
			logger.error("count Tag fail.the error is:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer deleteTag(Integer[] ids) {
		// TODO Auto-generated method stub
		try{
			String hql = "delete from Tag where id in (:ids)";

			Query query = this.getSession().createQuery(hql);
			query.setParameterList("ids", ids);
			
		    logger.info("delete tag success,and the ids="+ids);
			return query.executeUpdate();
		}catch(Exception e){
			logger.error("delete tag fail,and the ids="+ids+".the error is:"+e.getMessage());
			return null;
		}
	}
	
	@Override
	public Integer deleteTag(Integer id) {
		// TODO Auto-generated method stub
		try{
			String hql = "delete from Tag where id =:id";

			Query query = this.getSession().createQuery(hql);
			query.setInteger("id", id);
			
		    logger.info("delete tag success,and the id="+id);
			return query.executeUpdate();
		}catch(Exception e){
			logger.error("delete tag fail,and the id="+id+".the error is:"+e.getMessage());
			return null;
		}
	}

}
