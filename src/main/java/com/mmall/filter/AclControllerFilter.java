package com.mmall.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.model.SysUser;
import com.mmall.service.impl.SysCoreServiceImpl;
import com.mmall.util.ApplicationContextHelper;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
public class AclControllerFilter implements Filter {

    private static Set<String> exclusionSet = Sets.newConcurrentHashSet();

    private static final String noAuthUrl ="/sys/user/noAuth.page";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //定义白名单
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map parameterMap = request.getParameterMap();
        if(exclusionSet.contains(servletPath)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        SysUser currentUser = RequestHolder.getCurrentUser();
        if(currentUser==null){
            log.info("someone visit {} but no login. paramter {}",servletPath,JsonMapper.obj2String(parameterMap));
            noAuth(request,response);
            return;
        }
        SysCoreServiceImpl sysCoreServiceImpl = ApplicationContextHelper.popBean(SysCoreServiceImpl.class);
        if(!sysCoreServiceImpl.hasUrlAcl(servletPath)){
            log.info("{} visit {},but no login. paramter{}",JsonMapper.obj2String(currentUser.getUsername()),servletPath,JsonMapper.obj2String(parameterMap));
            noAuth(request,response);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }
    private void noAuth(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = request.getServletPath();
        if(url.endsWith(".json")){
            JsonData jsonData = JsonData.fail("没有权限访问页面,如有需要请联系管理员");
            response.setHeader("Content-Type","application/json");
            response.getWriter().print(JsonMapper.obj2String(jsonData));
            return;
        }else{
            clientRedirect(noAuthUrl,response);
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
