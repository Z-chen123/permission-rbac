package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.model.SysUser;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
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
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void saveDept(DeptParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept sysDept = SysDept.builder()
                .name(param.getName()).parentId(param.getParentId())
                .remark(param.getRemark()).seq(param.getSeq()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(sysDept.getParentId()),sysDept.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysDept.setOperateTime(new Date());
        this.sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    public void updateDept(DeptParam param) {
        BeanValidator.check(param);

        //更新前部门
        SysDept before = this.sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        if(checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept after = SysDept.builder().id(param.getId())
                .name(param.getName()).parentId(param.getParentId())
                .remark(param.getRemark()).seq(param.getSeq()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //TODO
        after.setOperateTime(new Date());

        //更新当前部门及子部门
        updateChildDept(before,after);
    }

    @Override
    public void delete(Integer id) {
        SysDept sysDept = this.sysDeptMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysDept,"待删除的部门不存在");
        if(sysDeptMapper.countByParentId(id)>0){
            throw new ParamException("待删除的部门存在子部门，无法删除");
        }
        if(sysUserMapper.countByDeptId(id)>0){
            throw new ParamException("待删除的部门存在用户，无法删除");
        }
        this.sysDeptMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新当前部门及子部门
     * @param before
     * @param after
     */
    @Transactional
    public void updateChildDept(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if(!newLevelPrefix.equals(oldLevelPrefix)){
            List<SysDept> sysDeptList = this.sysDeptMapper.getChildDeptLevelByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(sysDeptList)){
                for(SysDept sysDept:sysDeptList){
                    String level = sysDept.getLevel();
                    if(level.indexOf(oldLevelPrefix)==0){
                       level = newLevelPrefix+level.substring(oldLevelPrefix.length());
                       sysDept.setLevel(level);
                    }
                }
                this.sysDeptMapper.batchUpdateLevel(sysDeptList);
            }
        }
        this.sysDeptMapper.updateByPrimaryKey(after);
    }

    /**
     * 校验层级是否出现名称重复的部门
     */
    public boolean checkExist(Integer parentId,String deptName,Integer deptId){
        //TODO
        return this.sysDeptMapper.countByNameAndParentId(parentId,deptName,deptId)>0;
    }
    /**
     *  取出当前id下的level值
     */

    public String getLevel(Integer deptId){
        SysDept sysDept = this.sysDeptMapper.selectByPrimaryKey(deptId);
        if(sysDept==null){
            return null;
        }
        return sysDept.getLevel();
    }

}
