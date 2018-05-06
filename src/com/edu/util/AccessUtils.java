package com.edu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class AccessUtils {
	public static void recordIllegalArgumentAccess(HttpServletRequest request){
		Integer illegalArgumentCount = (Integer) request.getSession().getAttribute("illegalArgumentCount");
		
		if(illegalArgumentCount!=null){
			illegalArgumentCount += 1;
		}else{
			illegalArgumentCount = 1;
		}
		
		request.getSession().setAttribute("illegalArgumentCount",illegalArgumentCount);
	}
	
	public static void recordIpAccessTooMore(HttpServletRequest request){
		Integer ipAccessTooMoreCount = (Integer) request.getSession().getAttribute("ipAccessTooMoreCount");
		
		if(ipAccessTooMoreCount!=null){
			ipAccessTooMoreCount += 1;
		}else{
			ipAccessTooMoreCount = 1;
		}
		
		request.getSession().setAttribute("ipAccessTooMoreCount",ipAccessTooMoreCount);
	}
	
	public static String getIpAddr(HttpServletRequest request) throws UnknownHostException {  
		if (null == request) {
	            return null;
        }
 
        String proxs[] = { "X-Forwarded-For", "Proxy-Client-IP",
                "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
 
        String ip = null;
 
        for (String prox : proxs) {
            ip = request.getHeader(prox);
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                continue;
            } else {
                break;
            }
        }
 
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }
 
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(!StringUtils.isEmpty(ip) && ip.indexOf(",")>0){  
        	ip = ip.substring(0,ip.indexOf(","));  
        }
        
        return ip;
    }
	
	

    @SuppressWarnings("unchecked")
	public static String getAddresses(String ip, String encodingString) throws UnsupportedEncodingException {
        String returnStr = httpUrlConnection("http://ip.taobao.com/service/getIpInfo.php?ip="+ip,encodingString);

        if (!StringUtils.isEmpty(returnStr)) {
        	JSONObject jsonObject = JSON.parseObject(returnStr);
        	String code = jsonObject.getString("code");
        	
        	if(!StringUtils.isEmpty(code) && "0".equals(code)){
        		Map<String,Object> result =  (Map<String, Object>) jsonObject.get("data");
        		return "["+result.get("city").toString()+"]";
        	}
        	
        	return "";
        }
        
        return "";
    }  
    
    @SuppressWarnings("unchecked")
	public static String getAddressesMore(String ip, String encodingString) throws UnsupportedEncodingException {
        String returnStr = httpUrlConnection("http://ip.taobao.com/service/getIpInfo.php?ip="+ip,encodingString);

        if (!StringUtils.isEmpty(returnStr)) {
        	JSONObject jsonObject = JSON.parseObject(returnStr);
        	String code = jsonObject.getString("code");
        	
        	if(!StringUtils.isEmpty(code) && "0".equals(code)){
        		Map<String,Object> result =  (Map<String, Object>) jsonObject.get("data");
        	    StringBuilder sb = new StringBuilder();
        	
        	    sb.append(result.get("country"))
        	      .append(" ")
        	      .append(result.get("area"))
        	      .append(" ")
        	      .append(result.get("city"))
        	      .append(" ")
        	      .append(result.get("county"));
        	    
        		return sb.toString();
        	}
        	
        	return "";
        }
        
        return "";
    }  
  
    public static String httpUrlConnection(String url, String encoding) {
	       URL httpUrl = null;
	       HttpURLConnection connection = null;
	       
	       try {
		    	httpUrl = new URL(url);
		        connection = (HttpURLConnection) httpUrl.openConnection();// 新建连接实例
		        connection.setConnectTimeout(10000);// 设置连接超时时间，单位毫秒
		        connection.setReadTimeout(10000);// 设置读取数据超时时间，单位毫秒
		        connection.setDoOutput(true);// 是否打开输出流 true|false
		        connection.setDoInput(true);// 是否打开输入流true|false
		        connection.setRequestMethod("POST");// 提交方法POST|GET
		        connection.setUseCaches(false);// 是否缓存true|false
		        
		        connection.connect();// 打开连接端口
		        
		        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
		        StringBuffer buffer = new StringBuffer();
		        String line = "";
		        while ((line = reader.readLine()) != null) {
		         buffer.append(line);
		        }
		        reader.close();
		        return buffer.toString();
	       } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	       } finally {
		        if (connection != null) {
		         connection.disconnect();// 关闭连接
		        }
	       }
	}
	      
	      
	      
    /**
     * unicode 转换成 中文
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
}
