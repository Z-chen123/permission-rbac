package com.mmall.service;

import com.mmall.param.AclModuleParam;

public interface SysAclModuleService {

    void saveAclModule(AclModuleParam param);

    void updateAclModule(AclModuleParam param);

    void delete(Integer id);
}
