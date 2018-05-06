package com.edu.entity;

import java.util.List;
import java.util.Map;

public class Msg {

    private Integer status;//0成功；1失败
    
    private String msg;
    
    private Object obj;
    
    private Map<String,Object> result;
    
    private List list;
    
    public Msg(){}
    
    public Msg(Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }
    
    public Msg(Integer status,String msg,Object obj){
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }
    
    public Msg(Integer status,String msg,Map<String,Object> result){
        this.status = status;
        this.msg = msg;
        this.result = result;
    }
    
    public Msg(Integer status,String msg,List list){
        this.status = status;
        this.msg = msg;
        this.list = list;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
    
}
