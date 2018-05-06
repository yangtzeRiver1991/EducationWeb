package com.edu.validate;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.edu.entity.Msg;
import com.edu.util.StringUtils;




public class CommonValidate {
	private final static String VALIDATECODE_REGEX = "^[a-zA-Z0-9]{4}$";
	
	private final static String PARAM_REGEX = "^[a-zA-Z0-9]{1,50}$"; 
    
	public static Msg verificationCodeValidate(String verificationCode,HashMap<String,String> languageConfigParameter){
        if(StringUtils.isEmpty(verificationCode)){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_validate_code_input_empty_prompt"));
            return msg;
        }
        
        if(!Pattern.compile(VALIDATECODE_REGEX).matcher(verificationCode).matches()){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_validate_code_input_illegal_verification_code_prompt"));
            return msg;
        }
        
        return null;
    }
	
	public static Msg paramValidate(String param,HashMap<String,String> languageConfigParameter){
        if(StringUtils.isEmpty(param)){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_param_input_empty_prompt"));
            return msg;
        }
        
        if(!Pattern.compile(PARAM_REGEX).matcher(param).matches()){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_param_illegal_param_prompt"));
            return msg;
        }
        
        return null;
    }
    
    
}
