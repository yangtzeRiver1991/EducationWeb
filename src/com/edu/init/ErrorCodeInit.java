package com.edu.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.edu.util.StringUtils;

public class ErrorCodeInit {

    private final static Map<String,Object> ERRORCODE = new HashMap<String, Object>();
    
    private static Properties prop = new Properties();
	
	private static InputStream in;
    
    static{
    	try {
    		initErrorCode();
        	
        	in.close(); 
        } catch (IOException e) {   
            e.printStackTrace();   
        }
    }
    
    
    private static void initErrorCode() throws IOException{
    	in = ErrorCodeInit.class.getResourceAsStream("/config/error-code.properties"); 
        prop.load(in);  
        Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	ERRORCODE.put(entry.getKey().toString(), entry.getValue());
        }
    }
    
    public static Map<String,Object> getErrorCode(){
        return ERRORCODE;
    }
    
    public static String getCode(String errorCode){
    	if(!StringUtils.isEmpty(errorCode)){
    		return errorCode.substring(0,errorCode.indexOf("|"));
    	}
    	return "";
    }
    
    public static String getCodeExplain(String errorCode){
    	if(!StringUtils.isEmpty(errorCode)){
    		return errorCode.substring(errorCode.indexOf("|")+1,errorCode.length());
    	}
    	return "";
    }
    
}
