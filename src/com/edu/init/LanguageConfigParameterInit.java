package com.edu.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 配置文件参数读取并封装初始化
 * @author yangze
 * @createdate 2016-06-06
 * @version 1.0
 */
public class LanguageConfigParameterInit {
	private static HashMap<String,String> cnLanguageConfigParameter = new HashMap<String,String>();
	
	private static HashMap<String,String> enLanguageConfigParameter = new HashMap<String,String>();
	
	private static Properties prop = new Properties();
	
	private static InputStream in;
	
	static{     
        try {
        	initCnLanguageConfigParameter();
 
        	initEnLanguageConfigParameter();
        	
        	in.close();
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
	}
	
	private static HashMap<String,String> initCnLanguageConfigParameter() throws IOException{
		cnLanguageConfigParameter.put("language", "cn");
		
		in = LanguageConfigParameterInit.class.getResourceAsStream("/config/language/cn/common.properties"); 
        prop.load(in);  
        Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	cnLanguageConfigParameter.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        in = LanguageConfigParameterInit.class.getResourceAsStream("/config/language/cn/index.properties"); 
        prop.load(in);   
        iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	cnLanguageConfigParameter.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        in = LanguageConfigParameterInit.class.getResourceAsStream("/config/language/cn/manage.properties"); 
        prop.load(in);   
        iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	cnLanguageConfigParameter.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        return cnLanguageConfigParameter;
	}
	
	private static HashMap<String,String> initEnLanguageConfigParameter() throws IOException{
		enLanguageConfigParameter.put("language", "en");
		
		in = LanguageConfigParameterInit.class.getResourceAsStream("/config/language/en/common.properties"); 
        prop.load(in);   
        Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	enLanguageConfigParameter.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        in = LanguageConfigParameterInit.class.getResourceAsStream("/config/language/en/index.properties"); 
        prop.load(in);   
        iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	enLanguageConfigParameter.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        in = LanguageConfigParameterInit.class.getResourceAsStream("/config/language/en/manage.properties"); 
        prop.load(in);   
        iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	enLanguageConfigParameter.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        return enLanguageConfigParameter;
	}
	
	public static HashMap<String,String> getCnLanguageConfigParameter(){
		return cnLanguageConfigParameter;
	}
	
	public static HashMap<String,String> getEnLanguageConfigParameter(){
		return enLanguageConfigParameter;
	}
	
	public static HashMap<String,String> getLanguageConfigParameter(String languageConfig){
		if("cn".equals(languageConfig)){
			return cnLanguageConfigParameter;
		}
	    return enLanguageConfigParameter;
		
	}
}
