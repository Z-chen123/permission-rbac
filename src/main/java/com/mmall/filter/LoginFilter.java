package com.mmall.filter;

import com.mmall.common.RequestHolder;
import com.mmall.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String servletPath = req.getServletPath();
        SysUser user = (SysUser) req.getSession().getAttribute("user");
        if(user==null){
            String path ="/signin.jsp";
            resp.sendRedirect(path);
            return;
        }
        RequestHolder.add(user);
        RequestHolder.add(req);
        filterChain.doFilter(req,resp);
        return;
    }

    @Override
    public void destroy() {

    }
}
