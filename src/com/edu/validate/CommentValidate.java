package com.edu.validate;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.edu.entity.Msg;
import com.edu.init.EmotionsInit;
import com.edu.util.StringUtils;




public class CommentValidate extends CommonValidate{

    public static Msg commentValidate(String comment,HashMap<String,String> languageConfigParameter,HashMap<String,String> systemConfig){
        String commentSwitch = systemConfig.get("comment_switch");
        if(!"on".equals(commentSwitch)){
        	Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_switch_off"));
            return msg;
        }
    	
    	if(StringUtils.isEmpty(comment)){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_input_empty_promot"));
            return msg;
        }

        String commentContentLimit = "^[\u4E00-\u9FA5\uF900-\uFA2D\\n\\[\\]\\s\\、\\(\\)\\（\\）\\,\\，\\.\\。\\!\\！\\?\\？A-Za-z0-9_]{1,"+systemConfig.get("comment_content_word_limit")+"100}$";
        if(!Pattern.compile(commentContentLimit).matcher(comment).matches()){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_input_illegal_comment_content"));
            return msg;
        }
        
        int commentContentImgLimit = Integer.parseInt(systemConfig.get("comment_content_img_limit"));
        if(EmotionsInit.getEmotionNum(comment) > commentContentImgLimit){
            Msg msg = new Msg(1,languageConfigParameter.get("blog_index_comment_input_too_many_emotions"));
            return msg;
        }
        
        return null;
    }
    
    
    public static Msg publishCommentValidate(String comment,String verificationCode,HashMap<String,String> languageConfigParameter,HashMap<String,String> systemConfig){
        Msg msg = commentValidate(comment,languageConfigParameter,systemConfig);
        if(msg != null){
            return msg;
        }
         
        msg = verificationCodeValidate(verificationCode,languageConfigParameter);
        if(msg != null){
            return msg;
        }
        
        return null;
    }
    
}
