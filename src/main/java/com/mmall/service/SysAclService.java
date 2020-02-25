package com.mmall.service;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;

public interface SysAclService {

    void saveAcl(AclParam param);

    void updateAcl(AclParam param);

    PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery query);
}
