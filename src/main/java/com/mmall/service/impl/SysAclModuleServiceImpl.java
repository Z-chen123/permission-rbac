package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    @Override
    public void saveAclModule(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark()).build();
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(aclModule.getParentId()),aclModule.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());

        this.sysAclModuleMapper.insertSelective(aclModule);

    }

    /**
     * 更新当前权限模块及子权限模块
     * @param before
     * @param after
     */
    @Transactional
    public void updateChildAclModule(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if(!newLevelPrefix.equals(oldLevelPrefix)){
            List<SysAclModule> sysAclModuleList = this.sysAclModuleMapper.getChildAclModuleLevelByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(sysAclModuleList)){
                for(SysAclModule aclModule:sysAclModuleList){
                    String level = aclModule.getLevel();
                    if(level.indexOf(oldLevelPrefix)==0){
                        level = newLevelPrefix+level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                this.sysAclModuleMapper.batchUpdateLevel(sysAclModuleList);
            }
        }
        this.sysAclModuleMapper.updateByPrimaryKey(after);
    }

    @Override
    public void updateAclModule(AclModuleParam param) {
        BeanValidator.check(param);
        //更新前部门
        SysAclModule before = this.sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的权限模块不存在");
        if(checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule after = SysAclModule.builder().id(param.getId())
                .name(param.getName()).parentId(param.getParentId()).status(param.getStatus())
                .remark(param.getRemark()).seq(param.getSeq()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //TODO
        after.setOperateTime(new Date());

        //更新当前权限模块及子权限模块
        updateChildAclModule(before,after);
    }

    @Override
    public void delete(Integer id) {
        SysAclModule aclModule = this.sysAclModuleMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(aclModule,"待删除的权限模块不存在,无法删除");
        if(sysAclModuleMapper.countByParentId(id)>0){
            throw new ParamException("当前的权限模块下有子模块，无法删除");
        }
        if(sysAclMapper.countByAclModuleId(id)>0){
            throw new ParamException("当前权限模块下有权限点，无法删除");
        }
        this.sysAclModuleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 校验层级是否出现名称重复的权限模块
     */
    public boolean checkExist(Integer parentId,String aclModuleName,Integer aclModuleId){
        //TODO
        return this.sysAclModuleMapper.countByNameAndParentId(parentId,aclModuleName,aclModuleId)>0;
    }
    /**
     *  取出当前id下的level值
     */

    public String getLevel(Integer aclModuleId){
        SysAclModule sysAclModule = this.sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if(sysAclModule==null){
            return null;
        }
        return sysAclModule.getLevel();
    }
}
