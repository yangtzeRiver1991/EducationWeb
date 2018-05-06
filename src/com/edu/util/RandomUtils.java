package com.edu.util;

import java.util.Random;

public class RandomUtils {
    private static Random random = new Random();
	
    /**
     * 
     * @param digit
     * @param scope
     * @return
     */
	public static String randomCompose(int digit,int scope){
		String sRand="";
		for (int i=0;i<digit;i++){ 
			String rand=String.valueOf(random.nextInt(scope)); 
			sRand+=rand;  
		}
		
		return sRand;
	}
}
