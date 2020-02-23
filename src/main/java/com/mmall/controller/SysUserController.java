package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserServiceImpl;

    /**
     * 添加用户
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(UserParam param){
        sysUserServiceImpl.saveUser(param);
        return JsonData.success();
    }

    /**
     * 更新用户
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(UserParam param){
        sysUserServiceImpl.updateUser(param);
        return JsonData.success();
    }

    /**
     * 分页列表
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") Integer deptId, PageQuery query){
        PageResult<SysUser> result = this.sysUserServiceImpl.getPageByDeptId(deptId, query);
        return JsonData.success(result);
    }
}
