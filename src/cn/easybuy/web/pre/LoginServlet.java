package cn.easybuy.web.pre;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.easybuy.entity.User;
import cn.easybuy.service.user.UserService;
import cn.easybuy.service.user.UserServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.utils.SecurityUtils;
import cn.easybuy.web.AbstractServlet;
//用户注册
@WebServlet(urlPatterns={"/Login"},name="Login")
public class LoginServlet extends AbstractServlet {

	//注入用户业务类
	private UserService userService;
	
	public void init()throws ServletException{
		userService=new UserServiceImpl();//初始化
	}

	//重写相关方法
	public Class getServletClass() {
		return LoginServlet.class;
	}

	//跳转到登陆页面  反射
	public String toLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{
		return "/pre/login";
	}
	
	//对用户的账号密码进行接收  然后和数据库中的进行对比
	public ReturnResult login(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ReturnResult result=new ReturnResult();
//		PrintWriter outPrintWriter = response.getWriter();
		//获取前台用户输入的账号和密码
		String loginName=request.getParameter("loginName");
		String password=request.getParameter("password");
		//调用业务层里面的方法
		User user=userService.getUserByLoginName(loginName);
		//对用户进行非空判断
		if(EmptyUtils.isEmpty(user)){
			result.returnFail("用户不存在");
		}else{
			if(user.getPassword().equals(SecurityUtils.md5Hex(password))){//判断密码是否等于md5加密后的密码
				request.getSession().setAttribute("loginUser", user);//在session中存放一个user的信息
				result.returnSuccess("登陆成功");
			}else{
				result.returnFail("密码错误");
			}
		}
		return result;
	}

	public String loginOut(HttpServletRequest request, HttpServletResponse response){
		ReturnResult result=new ReturnResult();
		request.getSession().removeAttribute("loginUser");
		result.returnSuccess("注销成功 ");
		return  "/pre/login";
	}
	
}
