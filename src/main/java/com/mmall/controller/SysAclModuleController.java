package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import com.mmall.service.impl.SysDeptTreeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("sys/aclModule")
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleServiceImpl;
    
    @Resource
    private SysDeptTreeServiceImpl sysDeptTreeServiceImpl;

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
        sysAclModuleServiceImpl.saveAclModule(param);
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
        sysAclModuleServiceImpl.updateAclModule(param);
        return JsonData.success();
    }

    /**
     * 树结构
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        return JsonData.success(sysDeptTreeServiceImpl.aclModuleTree());
    }

    /**
     * 删除权限模块
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id")Integer id){
        this.sysAclModuleServiceImpl.delete(id);
        return JsonData.success();
    }
}
