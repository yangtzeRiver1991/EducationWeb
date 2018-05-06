package com.edu.entity;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable{
	private static final long serialVersionUID = 3475176635812155189L;

	private List<T> rows;
	
	private Integer total;
	
	private Integer nowPageNum;
	
	private Integer pageSize;
	
	private Integer allPageNum;
	
	private Integer status;
	
	private String sort;
	
	public PageResult(){
	}
	
	public PageResult(List<T> rows,Integer total,Integer status){
		this.rows=rows;
		this.total=total;
		this.status=status;
	}
	
	public PageResult(List<T> rows,Integer nowPageNum,Integer pageSize,Integer total,Integer status){
		this.rows=rows;
		this.nowPageNum=nowPageNum;
		this.pageSize=pageSize;
		this.total=total;
		this.status=status;
	}
	
	public PageResult(List<T> rows,Integer nowPageNum,Integer pageSize,Integer total,Integer status,String sort){
        this.rows=rows;
        this.nowPageNum=nowPageNum;
        this.pageSize=pageSize;
        this.total=total;
        this.status=status;
        this.sort=sort;
    }

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
	public Integer getNowPageNum() {
	    if(nowPageNum==null){
	        nowPageNum = 1;
	    }
		return nowPageNum;
	}

	public void setNowPageNum(Integer nowPageNum) {
		this.nowPageNum = nowPageNum;
	}

	public Integer getPageSize() {
	    if(pageSize == null){
	        pageSize = 10;
	    }
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getAllPageNum() {
		getPageSize();
		if(total%pageSize>0){
			allPageNum = total/pageSize + 1;
		}else{
			allPageNum = total/pageSize;
		}
		
		return allPageNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAllPageNum(Integer allPageNum) {
		this.allPageNum = allPageNum;
	}

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
