package com.edu.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.edu.util.StringUtils;

public class EmotionsInit {

    private final static Map<String,String> EMOTIONSURL = new HashMap<String, String>();
    
    private static Properties prop = new Properties();
	
	private static InputStream in;
    
    static{
    	try {
    		initEmotions();
        	
        	in.close(); 
        } catch (IOException e) {   
            e.printStackTrace();   
        }
    }
    
    private static void initEmotions() throws IOException{
    	in = EmotionsInit.class.getResourceAsStream("/config/emotions.properties"); 
        prop.load(in);  
        Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Object, Object> entry = iterator.next();
        	EMOTIONSURL.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }
    
    
    public static Map<String,String> getEmotionsUrl(){
        return EMOTIONSURL;
    }
    
    
    public static int getEmotionNum(String comment){
        char[] charArray = comment.toCharArray();
        int emotionNum = 0;
        StringBuilder sb = new StringBuilder();
        
        for(int i=0;i<charArray.length;i++){
            if(charArray[i] == '['){
                if(sb.toString().contains("[")){
                    sb = new StringBuilder("[");
                }else{
                    sb.append(charArray[i]);
                }
            }else if(charArray[i] == ']'){
                sb.append(charArray[i]);
                emotionNum++;
                sb = new StringBuilder();
            }else{
                if(!"".equals(sb.toString())){
                    sb.append(charArray[i]);
                }
            }
        }
        
        return emotionNum;
        
    }
    
    
    public static List<String> getEmotionsKey(String comment){
        char[] charArray = comment.toCharArray();
        List<String> emotionList = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        
        for(int i=0;i<charArray.length;i++){
            if(charArray[i] == '['){
                if(sb.toString().contains("[")){
                    sb = new StringBuilder("[");
                }else{
                    sb.append(charArray[i]);
                }
            }else if(charArray[i] == ']'){
                sb.append(charArray[i]);
                if(!emotionList.contains(sb.toString())){
                    emotionList.add(sb.toString());
                }
                sb = new StringBuilder();
            }else{
                if(!"".equals(sb.toString())){
                    sb.append(charArray[i]);
                }
            }
        }
        
        return emotionList;
        
    }
    
    public static String switchEmotionKey(String comment){
        List<String> emotionKeys = getEmotionsKey(comment);
        
        for(String emotionKey:emotionKeys){
            comment = comment.replaceAll(emotionKey.replace("[", "\\[").replace("]", "\\]"), StringUtils.switchEmptyStr(EMOTIONSURL.get(emotionKey)));    
        }
        
        return comment;
    }
}
