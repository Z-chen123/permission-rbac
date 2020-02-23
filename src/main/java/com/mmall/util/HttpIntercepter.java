package com.mmall.util;

import com.mmall.common.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  获取 请求路径，请求参数，耗时时间
 */
@Slf4j
public class HttpIntercepter extends HandlerInterceptorAdapter {
    private static final String START_TIME = "requestStartTime";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME,start);
        log.info("request start. url:{},params:{}",url,JsonMapper.obj2String(map));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request finished. url:{},params:{},cost:{}",url,JsonMapper.obj2String(map),end-start);
        removeThreadLocalInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        long start =(Long)request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request completed. url:{},params:{},cost:{}",url,JsonMapper.obj2String(map),end-start);
        removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo(){
        RequestHolder.remove();
    }
}
