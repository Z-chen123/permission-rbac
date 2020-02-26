package com.mmall.controller;

import com.google.common.collect.Maps;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysUserService;
import com.mmall.service.impl.SysDeptTreeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserServiceImpl;

    @Resource
    private SysDeptTreeServiceImpl sysDeptTreeServiceImpl;

    @Resource
    private SysRoleService sysRoleServiceImpl;

    /**
     * 没有权限访问的页面
     */
    @RequestMapping("/noAuth.page")
    @ResponseBody
    public ModelAndView noAuth(){
        return new ModelAndView("noAuth");
    }

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

    /**
     * 查看当前用户权限
     * @param userId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId")Integer userId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("acls",this.sysDeptTreeServiceImpl.userAclTree(userId));
        map.put("roles",this.sysRoleServiceImpl.getRoleListById(userId));
        return JsonData.success(map);
    }
}
