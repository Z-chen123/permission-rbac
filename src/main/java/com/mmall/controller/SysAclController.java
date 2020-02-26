package com.mmall.controller;

import com.google.common.collect.Maps;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysAcl;
import com.mmall.model.SysRole;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import com.mmall.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Resource
    private SysAclService sysAclServiceImpl;

    @Resource
    private SysRoleService sysRoleServiceImpl;


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

    /**
     * 查看当前用户权限
     * @param aclId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId")Integer aclId){
        Map<String,Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleServiceImpl.getRoleListByAclId(aclId);
        map.put("roles",roleList);
        map.put("users",this.sysRoleServiceImpl.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }


}
