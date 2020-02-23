package com.mmall.controller;

import com.mmall.model.SysUser;
import com.mmall.service.SysUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录
 */
@Controller
@RequestMapping
public class UserController {

    @Resource
    private SysUserService sysUserServiceImpl;

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        String path ="signin.jsp";
        response.sendRedirect(path);
    }

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser user = this.sysUserServiceImpl.findByKeyword(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");
        if(StringUtils.isBlank(username)){
            errorMsg="用户名不能为空";
        }else if(StringUtils.isBlank(password)){
            errorMsg="密码不能为空";
        }else if(user==null){
            errorMsg="查询不到用户";
        }else if(!user.getPassword().equals(MD5Util.encrypt(password))){
            errorMsg="用户名或密码错误";
        }else if(user.getStatus()!=1){
            errorMsg="用户已被冻结，请联系管理员";
        }else{
            // login success
            request.getSession().setAttribute("user",user);
            if(StringUtils.isNotBlank(ret)){
                response.sendRedirect(ret);
            }else{
                response.sendRedirect("/admin/index.page");
            }
            return;
        }
        request.setAttribute("errorMsg",errorMsg);
        request.setAttribute("username",username);
        if(StringUtils.isNotBlank(ret)){
            request.setAttribute("ret",ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request,response);

    }
}
