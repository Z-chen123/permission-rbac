package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.AclModuleParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sys/aclModule")
public class SysAclModuleController {

    /**
     *  进入权限模块页面
     */
    @RequestMapping("/acl.page")
    @ResponseBody
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    /**
     * 添加权限模块
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(AclModuleParam param){

        return JsonData.success();
    }

    /**
     * 更新权限模块
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(AclModuleParam param){

        return JsonData.success();
    }
}
