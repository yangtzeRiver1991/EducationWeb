package com.edu.util;

import java.util.LinkedList;
import java.util.List;

public class ArrayUtil {

	/**
	 * String[] 转换成 Integer[]
	 * @param strArray
	 * @return
	 */
	public static Integer[] convertToIntegerArray(String[] strArray){
		if(strArray!=null && strArray.length>0){
			Integer[] integerArray = new Integer[strArray.length];
			
			for(int i=0;i<strArray.length;i++){
				integerArray[i] = Integer.parseInt(strArray[i]);
			}
			
			return integerArray;
		}
		
		return null;
	} 
	
	
	/**
	 * 数组去重
	 * @param strArray
	 * @return
	 */
	public static String[] distinctArray(String[] strArray){
		List<String> list = new LinkedList<String>();  
		
	    for(int i = 0; i < strArray.length; i++) {  
	        if(!list.contains(strArray[i])) {  
	            list.add(strArray[i]);  
	        }  
	    }  
	    
	    return list.toArray(new String[list.size()]);
	}
}
