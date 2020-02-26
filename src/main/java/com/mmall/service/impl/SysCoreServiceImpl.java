package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        //我们定义一个假的管理角色，实际项目中我们可以从配置文件获取，也可以指定角色
        SysUser sysUser =RequestHolder.getCurrentUser();
        if(sysUser.getMail().startsWith("admin")){
            return true;
        }
        return false;
    }

    /**
     * 该请求路径是否有权限 true 有权限 false无权限
     */
    public boolean hasUrlAcl(String url){
        if(isSuperAdmin()){
            return true;
        }
        List<SysAcl> aclList = this.sysAclMapper.getUrl(url);
        boolean validateAcl = false;
        List<SysAcl> sysAclList = this.getCurrentUserAclList();
        Set<Integer> aclSet = sysAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());
        //只要有一个权限点有权限我们就认为有权限
        for(SysAcl acl : aclList){
            //一个用户是否具有某个权限点的访问权限
            if(acl==null||acl.getStatus()!=1){ //权限点无效
                continue;
            }
            validateAcl = true;
            if(aclSet.contains(acl.getId())){
                return true;
            }
        }
        if(!validateAcl){
            return true;
        }
        return false;
    }
}
