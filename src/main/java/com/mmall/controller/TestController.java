package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
@Slf4j
public class TestController {


    @RequestMapping("/hello1")
    @ResponseBody
    public String hello1(){
        log.info("hello1");
        return "hello permission";
    }

    /**
     *  .json 结尾的成功请求
     * @return
     */
    @RequestMapping(value = "/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");
      return JsonData.success("jsondata 访问成功");
    }

    /**
     * .json 结尾的请求permission异常 使用自定义异常
     */
    @RequestMapping(value = "/hello2.json")
    @ResponseBody
    public JsonData hello2(){
        log.info("hello2");
        throw new PermissionException("permission exception");
//        return JsonData.success("jsondata 访问成功");
    }

    /**
     * .json 结尾的请求其他异常 使用自定义系统异常
     */
    @RequestMapping(value = "/hello3.json")
    @ResponseBody
    public JsonData hello3(){
        log.info("hello3");
        throw new RuntimeException("permission exception");
//        return JsonData.success("jsondata 访问成功");
    }
}
