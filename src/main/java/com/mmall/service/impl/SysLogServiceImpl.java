package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.beans.LogType;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.*;
import com.mmall.dto.SearchLogDto;
import com.mmall.exception.ParamException;
import com.mmall.model.*;
import com.mmall.param.SearchLogParam;
import com.mmall.service.SysLogService;
import com.mmall.service.SysRoleAclService;
import com.mmall.service.SysRoleUserService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleAclService sysRoleAclServiceImpl;

    @Resource
    private SysRoleUserService sysRoleUserServiceImpl;

    public void saveDeptLog(SysDept before,SysDept after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after==null ? before.getId() : after.getId());
        sysLog.setOldValue(before==null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after ==null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        this.sysLogMapper.insertSelective(sysLog);

    }

    public void saveUserLog(SysUser before, SysUser after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after==null ? before.getId() : after.getId());
        sysLog.setOldValue(before==null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after ==null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        this.sysLogMapper.insertSelective(sysLog);
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after==null ? before.getId() : after.getId());
        sysLog.setOldValue(before==null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after ==null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        this.sysLogMapper.insertSelective(sysLog);
    }

    public void saveAclLog(SysAcl before, SysAcl after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after==null ? before.getId() : after.getId());
        sysLog.setOldValue(before==null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after ==null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        this.sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleLog(SysRole before, SysRole after){
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after==null ? before.getId() : after.getId());
        sysLog.setOldValue(before==null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after ==null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        this.sysLogMapper.insertSelective(sysLog);
    }





    @Override
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page) {
        BeanValidator.check(param);
        SearchLogDto dto = new SearchLogDto();
        dto.setType(param.getType());
        if(StringUtils.isNotBlank(param.getBeforeSeq())){
            dto.setBeforeSeq("%"+param.getBeforeSeq()+"%");
        }
        if(StringUtils.isNotBlank(param.getAfterSeq())){
            dto.setBeforeSeq("%"+param.getAfterSeq()+"%");
        }
        if(StringUtils.isNotBlank(param.getOperator())){
            dto.setBeforeSeq("%"+param.getOperator()+"%");
        }
        try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(param.getFromTime())) {
                dto.setFromTime(dateFormat.parse(param.getFromTime()));
            }
            if (StringUtils.isNotBlank(param.getToTime())) {
                dto.setToTime(dateFormat.parse(param.getToTime()));
            }
        }catch(Exception e){
            throw new ParamException("传入日期格式有问题，正确格式：yyyy-MM-dd HH:ss:mm");
        }
          int count = sysLogMapper.countBySearchDto(dto);
        if(count>0){
            List<SysLogWithBLOBs> logList = sysLogMapper.getPageListBySearchDto(dto,page);
            return PageResult.<SysLogWithBLOBs>builder().data(logList).total(count).build();
        }
        return PageResult.<SysLogWithBLOBs>builder().build();
    }

    @Override
    public void recover(Integer id) {
        SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysLog,"待还原的记录不存在");
        switch(sysLog.getType()){
            case LogType.TYPE_DEPT:
                SysDept before = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(before,"待还原的部门不存在");
                if(StringUtils.isBlank(sysLog.getNewValue())||StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增，删除操作不做还原");
                }
                SysDept after = JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysDept>() {
                });
                after.setOperator(RequestHolder.getCurrentUser().getUsername());
                after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                after.setOperateTime(new Date());
                this.sysDeptMapper.updateByPrimaryKeySelective(after);
                saveDeptLog(before,after);
                break;
            case LogType.TYPE_USER:
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeUser,"待还原的用户不存在");
                if(StringUtils.isBlank(sysLog.getNewValue())||StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增，删除操作不做还原");
                }
                SysUser afterUser = JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysUser>() {
                });
                afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperateTime(new Date());
                this.sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser,afterUser);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule,"待还原的权限模块不存在");
                if(StringUtils.isBlank(sysLog.getNewValue())||StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增，删除操作不做还原");
                }
                SysAclModule afterAclModule= JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysAclModule>() {
                });
                afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperateTime(new Date());
                this.sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule,afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl,"待还原的权限点不存在");
                if(StringUtils.isBlank(sysLog.getNewValue())||StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增，删除操作不做还原");
                }
                SysAcl afterAcl= JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysAcl>() {
                });
                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperateTime(new Date());
                this.sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl,afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole,"待还原的角色不存在");
                if(StringUtils.isBlank(sysLog.getNewValue())||StringUtils.isBlank(sysLog.getOldValue())){
                    throw new ParamException("新增，删除操作不做还原");
                }
                SysRole afterRole= JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysRole>() {
                });
                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperateTime(new Date());
                this.sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole,afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole,"待还原的角色权限关系不存在");
                this.sysRoleAclServiceImpl.changeRoleAcls(sysLog.getId(),JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysRoleAcl>() {
                }));
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole userRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole,"待还原的角色用户关系不存在");
                this.sysRoleUserServiceImpl.changeRoleUsers(sysLog.getId(),JsonMapper.string2Obj(sysLog.getOldValue(),new TypeReference<SysRoleUser>(){}));
                break;
                default:;
        }
    }
}
