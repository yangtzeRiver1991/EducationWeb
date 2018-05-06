package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.edu.dao.CacheManageDao;
import com.edu.entity.CacheManage;
import com.edu.util.StringUtils;

@Repository("cacheManageDao") 
public class CacheManageDaoImpl extends HibernateDaoSupport implements CacheManageDao{
	private Logger logger = Logger.getLogger(CacheManageDaoImpl.class);

	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	

	@Override
	public CacheManage insertCacheManage(CacheManage cacheManage) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().save(cacheManage);
			
			logger.info("add cacheManage success,and the id="+cacheManage.getId());
			return cacheManage;
		}catch(Exception e){
			logger.error("add cacheManage fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CacheManage> findCacheManageList() {
		// TODO Auto-generated method stub
		try{
			String hql = "from CacheManage";
			Query query = this.getSession().createQuery(hql);
			
			List<CacheManage> cacheManageList = query.list();;
			
		    logger.info("find cacheManageList success");
			return cacheManageList;
		}catch(Exception e){
			logger.error("find cacheManageList fail:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer updateCacheManage(CacheManage cacheManage) {
		// TODO Auto-generated method stub
		try{
			if(cacheManage != null){
				StringBuffer hql = new StringBuffer("update CacheManage set updateDate =:updateDate");
				
				if(!StringUtils.isEmpty(cacheManage.getCode())){
					hql.append(" ,code= :code");
				}
				if(!StringUtils.isEmpty(cacheManage.getName())){
					hql.append(" ,name= :name");
				}
				if(!StringUtils.isEmpty(cacheManage.getDescrition())){
					hql.append(" ,descrition= :descrition");
				}
				if(cacheManage.isAllEntries() != null){
					hql.append(" ,allEntries= :allEntries");
				}
				
				hql.append(" where id= :id");
				
				Query query = this.getSession().createQuery(hql.toString());

				query.setString("updateDate", cacheManage.getUpdateDate());
				if(!StringUtils.isEmpty(cacheManage.getCode())){
					query.setString("code", cacheManage.getCode());
				}
				if(!StringUtils.isEmpty(cacheManage.getName())){
					query.setString("name", cacheManage.getName());
				}
				if(!StringUtils.isEmpty(cacheManage.getDescrition())){
					query.setString("descrition", cacheManage.getDescrition());
				}
				if(cacheManage.isAllEntries() != null){
					query.setBoolean("allEntries", cacheManage.isAllEntries());
				}
				
				query.setInteger("id", cacheManage.getId());
				
				logger.info("update cacheManage success,and the id="+cacheManage.getId());
				return query.executeUpdate();
				
			}
			
			return null;
			
		}catch(Exception e){
			logger.error("update cacheManage fail,and the id="+cacheManage.getId()+".and the error is:"+e.getMessage());
			return null;
		}
	}

}
