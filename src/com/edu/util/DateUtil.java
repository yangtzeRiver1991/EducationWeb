package com.edu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private final static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    
    private final static SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private final static SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    

    /**
     * getDay(取得当前日期，以yyyyMMdd的形式)   
     * @return
     */
    public static String getDay(){
        return YYYYMMDD.format(new Date());
    }
    
    
    /**
     * getTime(取得当前日期，以yyyyMMddHHmmss的形式)   
     * @return
     */
    public static String getTime(){
        return YYYYMMDDHHMMSS.format(new Date());
    }
    
    /**
     * getTime(取得当前日期，以yyyy-MM-dd HH-mm-ss的形式)   
     * @return
     */
    public static String getTimeTwo(){
        return YYYY_MM_DD_HH_MM_SS.format(new Date());
    }
    
    
    /**
     * 计算两日期间隔
     * @param beginDate
     * @param endDate
     * @param unit
     * @return
     */
    public static long calculateDateIntervals(Date beginDate,Date endDate,String unit){
    	if("second".equals(unit)){
    		return (endDate.getTime() - beginDate.getTime())/1000;
    	}
    	else if("minute".equals(unit)){
    		return (endDate.getTime() - beginDate.getTime())/(1000 * 60);
    	}
    	else if("hour".equals(unit)){
    		return (endDate.getTime() - beginDate.getTime())/(1000 * 60 * 60);
    	}
    	else if("day".equals(unit)){
    		return (endDate.getTime() - beginDate.getTime())/(1000 * 60 * 60 * 24);
    	}else{
    		return 0;
    	}
    }
}
