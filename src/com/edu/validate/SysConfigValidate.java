package com.edu.validate;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.edu.entity.Msg;
import com.edu.util.StringUtils;




public class SysConfigValidate extends CommonValidate{
    private final static String LANGUAGE_CONFIG_REGEX = "^en|cn$"; 
    
    public static Msg languageConfigValidate(String languageConfig,String verificationCode,HashMap<String,String> languageConfigParameter){
    	if(StringUtils.isEmpty(languageConfig)){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_common_language_set_empty_prompt"));
            return msg;
        }
        
        if(!Pattern.compile(LANGUAGE_CONFIG_REGEX).matcher(languageConfig).matches()){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_common_language_set_illegal_prompt"));
            return msg;
        }
        
         
        Msg msg = verificationCodeValidate(verificationCode,languageConfigParameter);
        if(msg != null){
            return msg;
        }
        
        return null;
    }
    
    
}
