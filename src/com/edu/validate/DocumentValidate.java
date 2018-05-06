package com.edu.validate;

import java.util.regex.Pattern;

import com.edu.entity.Msg;
import com.edu.util.StringUtils;




public class DocumentValidate extends CommonValidate{
    private final static String TITLE_REGEX = "^[A-Za-z0-9_\u4E00-\u9FA5\uF900-\uFA2D]{1,100}$";
    
    private final static String TAG_REGEX = "^all|java|webindex|server|database|tools|system|other|aboutme$";
    
    private final static String CHILD_TAG_REGEX = "^[A-Za-z0-9]{1,50}$";

    public static Msg titleValidate(String title){
        if(StringUtils.isEmpty(title)){
            Msg msg = new Msg(1,"文章标题不能为空");
            return msg;
        }
        
        if(!Pattern.compile(TITLE_REGEX).matcher(title).matches()){
            Msg msg = new Msg(1,"文章标题不符合规范");
            return msg;
        }
        
        return null;
    }
    
    
    public static Msg searchContentValidate(String searchContent){
        if(StringUtils.isEmpty(searchContent)){
        	Msg msg = new Msg(1,"搜索内容不能为空");
            return msg;
        }
        if(searchContent.length()>500){
            Msg msg = new Msg(1,"搜索内容过长");
            return msg;
        }
        
        return null;
    }
    
    
    public static Msg searchTagValidate(String tag){
        if(StringUtils.isEmpty(tag)){
        	Msg msg = new Msg(1,"文章标签不能为空");
            return msg;
        }
        if(!Pattern.compile(TAG_REGEX).matcher(tag).matches()){
            Msg msg = new Msg(1,"文章标签不符合规范");
            return msg;
        }
        
        return null;
    }
    
    public static Msg childTagValidate(String tag){
    	if(StringUtils.isEmpty(tag)){
            Msg msg = new Msg(1,"标签不能为空");
            return msg;
        }
        
        if(!Pattern.compile(CHILD_TAG_REGEX).matcher(tag).matches()){
            Msg msg = new Msg(1,"标签不符合规范");
            return msg;
        }
        
        return null;
    }
    
    
    public static Msg searchValidate(String searchContent,String tag){
        Msg msg = searchContentValidate(searchContent);
        if(msg!=null){
            return msg;
        }
        
        msg = searchTagValidate(tag);
        if(msg!=null){
            return msg;
        }
        
        return null;
    }
}
