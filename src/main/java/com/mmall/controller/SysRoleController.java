package com.mmall.controller;

import com.google.common.collect.Lists;
import com.mmall.common.JsonData;
import com.mmall.model.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.service.SysRoleAclService;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysRoleUserService;
import com.mmall.service.SysUserService;
import com.mmall.service.impl.SysDeptTreeServiceImpl;
import com.mmall.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleServiceImpl;

    @Resource
    private SysDeptTreeServiceImpl sysDeptTreeServiceImpl;

    @Resource
    private SysRoleAclService sysRoleAclServiceImpl;

    @Resource
    private SysRoleUserService sysRoleUserServiceImpl;

    @Resource
    private SysUserService sysUserServiceImpl;

    /**
     * 进入角色页面
     * @param param
     * @return
     */
    @RequestMapping("/role.page")
    public ModelAndView page(RoleParam param){
        return new ModelAndView("role");
    }


    /**
     * 添加角色
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(RoleParam param){
        sysRoleServiceImpl.saveRole(param);
        return JsonData.success();
    }

    /**
     * 更新角色
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(RoleParam param){
        sysRoleServiceImpl.updateRole(param);
        return JsonData.success();
    }

    /**
     * 查询所有角色
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list(){
        return JsonData.success(this.sysRoleServiceImpl.getAll());
    }

    /**
     * 角色权限树
     * @return
     */
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId){
        return JsonData.success(this.sysDeptTreeServiceImpl.roleTree(roleId));
    }

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,@RequestParam("aclIds")String aclIds){
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        this.sysRoleAclServiceImpl.changeRoleAcls(roleId,aclIdList);
        return JsonData.success();
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId,@RequestParam("userIds")String userIds){
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        this.sysRoleUserServiceImpl.changeRoleUsers(roleId,userIdList);
        return JsonData.success();
    }

    /**
     * 查询已选中与未选中的用户
     * @param roleId
     * @return
     */
    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        //具有该角色权限下的用户(已选择)
        List<SysUser> selectedList = sysRoleUserServiceImpl.getListByRoleId(roleId);
        //未选择 = 总用户 - 已选择
        List<SysUser> unselectedList = Lists.newArrayList();

        List<SysUser> allUserList = this.sysUserServiceImpl.getAll();
        Set<Integer> selectedSet = selectedList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for(SysUser user : allUserList){
            if(user.getStatus()==1 && !selectedSet.contains(user.getId())){
                unselectedList.add(user);
            }
        }
        //去掉已选中的状态不等于1的用户
//        selectedList = selectedList.stream().filter(sysUser -> sysUser.getStatus()!=1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = new HashMap<>();
        map.put("selected",selectedList);
        map.put("unselected",unselectedList);
        return JsonData.success(map);
    }
}
