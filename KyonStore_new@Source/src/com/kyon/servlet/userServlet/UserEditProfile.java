package com.kyon.servlet.userServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.User;

@WebServlet("/user-edit-profile")
public class UserEditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao ud = new UserDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息 
    	String uId = "";	// 改为从Session获取
    	String uMail = "";
    	String uPwd = "";
    	double uBalance = 0.0;
//    	if(req.getParameter("uId")!=null)
//    		uId = req.getParameter("uId");
    	if(req.getParameter("uMail")!=null)
    		uMail = req.getParameter("uMail");
    	if(req.getParameter("uPwd")!=null)
    		uPwd = req.getParameter("uPwd");
    	if(req.getParameter("uBalance")!=null)
    		uBalance = Double.parseDouble(req.getParameter("uBalance"));
    	
    	// 从Session获取uId
    	Object u = req.getAttribute("user");
    	if(u != null) {
    		uId = ((User)u).getuId();
    	} else {
    		// 用户未登录，直接响应
    		String resp_str = "{\"isLogin\":0,\"success\":0}";
        	resp.getWriter().write(resp_str);
        	return;
    	}
    	
    	// 到这里说明用户已登录
    	System.out.println("user_edit_profile["+uId+","+uMail+","+uPwd+","+uBalance+"]");
    	
    	// 处理请求信息
    	int success = 0;
    	try {
    		int flag = ud.editProfile(uId, uMail, uPwd, uBalance);
    		System.out.println("flag="+flag);
    		if(flag==1)
    			success = 1;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"isLogin\":1,\"success\":"+success+"}";
    	resp.getWriter().write(str);
	}

}
