package com.mmall.common;

import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  处理异常的类
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    /**
     *  异常处理规则
     * @param request
     * @param response
     * @param object
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        //自定义系统异常
        String defaultMsg = "System.Error";
        //.json,.page 用各自异常处理

        // 这里要求所有请求json 数据的都以.json结尾
        if(url.endsWith(".json")){
            if(ex instanceof PermissionException|| ex instanceof ParamException){
                JsonData result = JsonData.fail(ex.getMessage());
                mv = new ModelAndView("jsonView",result.toMap());
            }else{
                log.error("unknown json exception, url:"+url,ex);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",result.toMap());
            }
        }else if(url.endsWith(".page")){ // 这里要求所有请求page页面的都以.page结尾
                log.error("unknown page exception, url:"+url,ex);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("exception",result.toMap());

        }else{
            log.error("unknown exception, url:"+url,ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",result.toMap());
        }
        return mv;
    }
}
