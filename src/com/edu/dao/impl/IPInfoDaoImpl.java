package com.edu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.edu.dao.IPInfoDao;
import com.edu.entity.IPInfo;

@Repository("iPInfoDao") 
public class IPInfoDaoImpl extends HibernateDaoSupport implements IPInfoDao{
	private Logger logger = Logger.getLogger(IPInfoDaoImpl.class);

	@Resource  
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public IPInfo insertIPInfo(IPInfo iPInfo) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().save(iPInfo);
			
			logger.info("add iPInfo success,and the id="+iPInfo.getId());
			return iPInfo;
		}catch(Exception e){
			logger.error("add iPInfo fail:"+e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IPInfo findIPInfo(String ip) {
		// TODO Auto-generated method stub
		try{
			String hql = "from IPInfo where ip= :ip";
			
			Query query = this.getSession().createQuery(hql);
			query.setString("ip", ip);
			
			List<IPInfo> iPInfoList = (List<IPInfo>)query.list();
			if(iPInfoList!=null && iPInfoList.size()>0){
				logger.info("find findIPInfo success,and the ip="+ip);
				return iPInfoList.get(0);
			}
			
		    logger.info("find findIPInfo fail,and the ip="+ip);
			return null;
		}catch(Exception e){
			logger.error("find findIPInfo fail,and the ip="+ip+".the error is:"+e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IPInfo> findIPInfoList(Integer status, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("from IPInfo where 1=1");
			
			if(status!=null){
				hql.append(" and status= :status");
			}

			Query query = this.getSession().createQuery(hql.toString());
			
			if(status!=null){
				query.setInteger("status", status);
			}
			
			List<IPInfo> iPInfoList;
			
			if(offset!=null && limit!=null){
				iPInfoList = (List<IPInfo>) query.setFirstResult(offset)
							                     .setMaxResults(limit)
							                     .list();
			}else{
				iPInfoList = (List<IPInfo>) query.list();
			}
			
		    logger.info("find iPInfoList success");
		    
			return iPInfoList;
		}catch(Exception e){
			logger.error("find iPInfoList fail:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer updateIPInfo(String ip,Integer status, String descrition) {
		// TODO Auto-generated method stub
		try{
			String hql = "update IPInfo set status= :status,descrition= :descrition where ip= :ip";

			Query query = this.getSession().createQuery(hql);
			
			query.setInteger("status", status);
			query.setString("descrition", descrition);
			query.setString("ip", ip);
			
			logger.info("update updateIPInfo success,and the ip="+ip);
			return query.executeUpdate();
			
		}catch(Exception e){
			logger.error("update updateIPInfo fail,and the ip="+ip+".and the error is:"+e.getMessage());
			return null;
		}

	}
	
	@Override
	public Integer updateIPInfo(String ip,String languageConfigParameter) {
		// TODO Auto-generated method stub
		try{
			String hql = "update IPInfo set languageConfigParameter= :languageConfigParameter where ip= :ip";

			Query query = this.getSession().createQuery(hql);
			
			query.setString("languageConfigParameter", languageConfigParameter);
			query.setString("ip", ip);
			
			logger.info("update updateIPInfo success,and the ip="+ip);
			return query.executeUpdate();
			
		}catch(Exception e){
			logger.error("update updateIPInfo fail,and the ip="+ip+".and the error is:"+e.getMessage());
			return null;
		}

	}

	@Override
	public Integer countIPInfoNum(Integer status) {
		// TODO Auto-generated method stub
		try{
			StringBuffer hql = new StringBuffer("select count(*) from IPInfo where 1=1");
			
	        if(status != null){
	    		hql.append(" and status = :status");
	    	}

			Query query = this.getSession().createQuery(hql.toString());
			
			if(status != null){
				query.setInteger("status",status);
	    	}

		    int i = 0;
		    
			i = ((Long)query.uniqueResult()).intValue();
			logger.info("countIPInfoNum success");
		    
		    return i;
		}catch(Exception e){
			logger.error("countIPInfoNum fail:"+e.getMessage());
			return null;
		}
	}

}
