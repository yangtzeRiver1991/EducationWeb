package com.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.entity.CourseSign;
import com.edu.entity.Msg;
import com.edu.entity.User;
import com.edu.init.ErrorCodeInit;
import com.edu.service.CourseSignService;
import com.edu.service.UserService;
import com.edu.util.AccessUtils;
import com.edu.util.MD5Code;

@Controller
public class UserController {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Resource
	private UserService userService;
	
	@Resource
	private CourseSignService courseSignService;
	
	@RequestMapping(value="user/exit")
	public String userQuit(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();

    	return "redirect:/";
	}
	
	
	@RequestMapping(value="user/register")
	@ResponseBody 
	public Msg userRegister(@ModelAttribute User user,String verificationCode,HttpServletRequest request) {
	    if(userService.findUser(user.getUsername()) != null){
	    	Msg nowMsg = new Msg(1,"该用户名已被注册");
			return nowMsg;
	    }
	    
	    try{
	    	User insertUser = new User();
	    	insertUser.setUsername(user.getUsername());
	    	insertUser.setMobile(user.getMobile());
	    	insertUser.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
	    	insertUser.setCreateDate(simpleDateFormat.format(new Date()));
	    	insertUser.setCreaterIp(AccessUtils.getIpAddr(request));
	    	
	    	userService.insertUser(insertUser);
	    	
	    	Msg nowMsg = new Msg(0,"注册成功");
			return nowMsg;
	    }catch(Exception e){
	    	e.printStackTrace();
	    	Msg nowMsg = new Msg(1,"注册失败");
			return nowMsg;
	    }
	        
	}
	
	@RequestMapping(value="user/login")
	@ResponseBody 
	public Msg userLogin(@ModelAttribute User user,String verificationCode,HttpServletRequest request) {
	    User nowUser = userService.userLogin(user.getUsername(), new MD5Code().getMD5ofStr(user.getPassword()));
	    if(nowUser == null){
	    	Msg msg = new Msg(1,"用户名或密码错误");
	    	return msg;
	    }
	    
	    request.getSession().setAttribute("user", nowUser); 
	    
	    Msg msg = new Msg(0,"登陆成功");
    	return msg;
	}
	
	
	@RequestMapping(value="user/courseSign")
	@ResponseBody 
	public Msg courseSign(Integer courseId,String courseName,HttpServletRequest request) {
	    User nowUser = (User) request.getSession().getAttribute("user");
	    if(nowUser == null){
	    	Msg msg = new Msg(1,"请先登陆");
	    	return msg;
	    }
	    
	    List<CourseSign> courseSignList = courseSignService.findByUserCourse(nowUser.getId(), courseId);
	    if(courseSignList != null && courseSignList.size()>0){
	    	Msg msg = new Msg(1,"你已经报名过该课程，无需再次报名");
	    	return msg;
	    }
	    
	    try{
	    	CourseSign insertCourseSign = new CourseSign();
	    	
	    	insertCourseSign.setUserId(nowUser.getId());
	    	insertCourseSign.setCourseName(courseName);
	    	insertCourseSign.setCourseId(courseId);
	    	insertCourseSign.setProgress(0);
	    	insertCourseSign.setCreateDate(simpleDateFormat.format(new Date()));
	    	insertCourseSign.setCreaterIp(AccessUtils.getIpAddr(request));
	    	
	    	courseSignService.insertCourseSign(insertCourseSign);
	    	
	    	Msg nowMsg = new Msg(0,"课程报名成功");
			return nowMsg;
	    }catch(Exception e){
	    	e.printStackTrace();
	    	Msg nowMsg = new Msg(1,"课程报名失败");
			return nowMsg;
	    }
	}
	
	@RequestMapping(value = "user/myCourse")  
    public String myCourse(HttpServletRequest request,Model model) {
		User nowUser = (User) request.getSession().getAttribute("user");
		if(nowUser == null){
			model.addAttribute("needLogin", "yes");
			return "redirect:/";
		}
		
        try{
        	List<CourseSign> courseSignList = courseSignService.findByUser(nowUser.getId(), null, null);
        	model.addAttribute("courseSignList", courseSignList);

            return "WEB-INF/myCourse";
        }catch(Exception e){
            e.printStackTrace();
            
            model.addAttribute("errorCode", ErrorCodeInit.getCode(ErrorCodeInit.getErrorCode().get("docuemntListIndex").toString()));
            return "WEB-INF/error";
        }
        
    }
	
}
