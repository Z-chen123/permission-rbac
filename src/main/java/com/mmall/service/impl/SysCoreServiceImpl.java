package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.model.SysAcl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  权限的核心类
 */
@Service
public class SysCoreServiceImpl {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 获取当前用户的所有权限
     * @return
     */
    public List<SysAcl> getCurrentUserAclList(){
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    /**
     * 获取当前角色的所有权限
     */
    public List<SysAcl> getRoleAclList(int roleId){
        List<Integer> aclIdList = this.sysRoleAclMapper.getAclListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if(CollectionUtils.isEmpty(aclIdList)){
            return Lists.newArrayList();
        }
        return this.sysAclMapper.getAclByIdList(aclIdList);
    }

    /**
     * 获取用户的所有权限
     * @return
     */
    public List<SysAcl> getUserAclList(int userId){
        if(isSuperAdmin()){
            List<SysAcl> list = this.sysAclMapper.getAll();
            return list;
        }
        //1.查询该用户的所有角色
        List<Integer> userRoleIdList = sysAclMapper.getRoleIdListByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleIdList)){
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = this.sysRoleAclMapper.getAclListByRoleIdList(userRoleIdList);
        if(CollectionUtils.isEmpty(userAclIdList)){
            return Lists.newArrayList();
        }
        return this.sysAclMapper.getAclByIdList(userAclIdList);
    }

    public boolean isSuperAdmin(){
        return true;
    }
}
