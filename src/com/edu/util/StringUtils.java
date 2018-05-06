package com.edu.util;

import java.text.NumberFormat;

/**
 * 字符串判断相关工具
 * @author yangze
 *
 */
public class StringUtils {
   /**
    * 判断是字符串否非空
    * @return boolean: true(为空) false(非空)
    */
	public static boolean isEmpty(String str){
		if(str==null || str.trim().equals("")){
			return true;
		}
		return false;
	}

   /**
    * 判断是字符串否非空
    * @return boolean: true(为空) false(非空)
    */
	 public static String switchEmptyStr(String str){
	     if(isEmpty(str)){
	         return "";
	     }
	     
	     return str.trim();
	 }
	
	
	
	/**
	 * 百分比转换
	 * @params divisor(除数) dividend(被除数) location(精确小数点后几位数)
	 * @return 
	 */
	public static String formatScale(Integer divisor,Integer dividend,int location){
		if(dividend==null || dividend==0){
			return "0";
		}
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(location);

		return nf.format(divisor*1.0/dividend);
	}
}
