package com.mmall.service;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.model.SysRoleAcl;
import com.mmall.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    
    @Override
    public void changeRoleAcls(int roleId, List<Integer> aclIdList) {
        //判断未修改之前的权限与修改后权限是否一致
        List<Integer> originAcls = this.sysRoleAclMapper.getAclListByRoleIdList(Lists.newArrayList(roleId));
        if(originAcls.size()==aclIdList.size()){
            HashSet<Integer> originSet = new HashSet<>(originAcls);
            HashSet<Integer> aclSet = new HashSet<>(aclIdList);
            originSet.removeAll(aclSet);
            if(CollectionUtils.isEmpty(originSet)){
                return;
            }
        }
            updateRoleAcl(roleId,aclIdList);
    }
    @Transactional
    public void updateRoleAcl(int roleId, List<Integer> aclIdList) {
        //删除之前权限
        this.sysRoleAclMapper.deleteByRoleId(roleId);
        //新增修改后权限
        List<SysRoleAcl> sysRoleAcls = Lists.newArrayList();
        for(Integer aclId :aclIdList){
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
            sysRoleAcls.add(sysRoleAcl);
        }
        this.sysRoleAclMapper.batchInsert(sysRoleAcls);
    }
}
