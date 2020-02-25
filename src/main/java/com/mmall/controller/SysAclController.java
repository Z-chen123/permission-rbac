package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Resource
    private SysAclService sysAclServiceImpl;

    /**
     * 添加部门
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(AclParam param){
        this.sysAclServiceImpl.saveAcl(param);
        return JsonData.success();
    }

    /**
     * 更新部门
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(AclParam param){
        this.sysAclServiceImpl.updateAcl(param);
        return JsonData.success();
    }

    /**
     *  获取权限列表
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("aclModuleId")Integer aclModuleId, PageQuery query){
        PageResult<SysAcl> result = this.sysAclServiceImpl.getPageByAclModuleId(aclModuleId, query);
        return JsonData.success(result);
    }


}
