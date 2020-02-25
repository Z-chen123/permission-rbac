package com.mmall.service;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;

import java.util.List;

public interface SysUserService {

    void saveUser(UserParam param);

    void updateUser(UserParam param);

    SysUser findByKeyword(String username);

    PageResult<SysUser> getPageByDeptId(int deptId, PageQuery query);

    List<SysUser> getAll();
}
