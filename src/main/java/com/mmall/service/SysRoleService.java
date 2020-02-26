package com.mmall.service;


import com.mmall.model.SysRole;
import com.mmall.model.SysUser;
import com.mmall.param.RoleParam;

import java.util.List;

public interface SysRoleService {

    void saveRole(RoleParam param);

    void updateRole(RoleParam param);

    List<SysRole> getAll();

    List<SysRole> getRoleListById(Integer userId);

    List<SysRole> getRoleListByAclId(Integer aclId);

    List<SysUser> getUserListByRoleList(List<SysRole> roleList);
}
