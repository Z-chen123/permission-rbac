package com.mmall.service;


import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;

import java.util.List;

public interface SysRoleService {

    void saveRole(RoleParam param);

    void updateRole(RoleParam param);

    List<SysRole> getAll();
}
