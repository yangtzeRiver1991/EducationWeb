package com.edu.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SystemConfigInit {

    private final static Map<String,String> SYSTEMCONFIG = new HashMap<String, String>();
    
    private static Properties prop = new Properties();
	
	private static InputStream in;
    
    static{
    	try {
    		initSystemConfig();
        	
        	in.close(); 
        } catch (IOException e) {   
            e.printStackTrace();   
        }
    }
    
    private static void initSystemConfig() throws IOException{
    	in = SystemConfigInit.class.getResourceAsStream("/config/system-config.properties"); 
        prop.load(in);  
        Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	SYSTEMCONFIG.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }
    
    
    public static Map<String,String> getSystemConfig(){
        return SYSTEMCONFIG;
    }
}
