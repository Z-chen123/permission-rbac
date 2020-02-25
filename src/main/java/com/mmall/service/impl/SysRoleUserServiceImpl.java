package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.model.SysRoleUser;
import com.mmall.model.SysUser;
import com.mmall.service.SysRoleUserService;
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
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = this.sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return this.sysUserMapper.getByIdList(userIdList) ;
    }

    @Override
    public void changeRoleUsers(int roleId,List<Integer> userIdList) {
        //判断未修改之前的用户与修改后用户是否一致
        List<Integer> originUsers = this.sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(originUsers.size()==userIdList.size()){
            HashSet<Integer> originSet = new HashSet<>(originUsers);
            HashSet<Integer> userSet = new HashSet<>(userIdList);
            originSet.removeAll(userSet);
            if(CollectionUtils.isEmpty(originSet)){
                return;
            }
        }
        updateRoleUsers(roleId,userIdList);
    }
    @Transactional
    public void updateRoleUsers(int roleId, List<Integer> userIdList) {
        this.sysRoleUserMapper.deleteByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)){
            return;
        }
        List<SysRoleUser> sysRoleUserList = Lists.newArrayList();
        for(Integer userId : userIdList){
            SysRoleUser sysRoleUser = SysRoleUser.builder().roleId(roleId).userId(userId)
                    .operator(RequestHolder.getCurrentUser().getUsername()).operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()
                    )).operateTime(new Date()).build();
            sysRoleUserList.add(sysRoleUser);
        }
        this.sysRoleUserMapper.batchInsert(sysRoleUserList);
    }
}
