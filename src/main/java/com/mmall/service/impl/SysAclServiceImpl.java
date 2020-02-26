package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import com.mmall.service.SysLogService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclServiceImpl implements SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysLogService sysLogServiceImpl;

    @Override
    public void saveAcl(AclParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getAclModuleId(),param.getName(),param.getId())){
            throw new ParamException("同一权限模块是否存在相同的权限点");
        }
        SysAcl acl  = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        acl.setCode(genericCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        acl.setOperateTime(new Date());

        this.sysAclMapper.insertSelective(acl);
        this.sysLogServiceImpl.saveAclLog(null,acl);
    }

    @Override
    public void updateAcl(AclParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getAclModuleId(),param.getName(),param.getId())){
            throw new ParamException("同一权限模块是否存在相同的权限点");

        }
        SysAcl sysAcl = this.sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(sysAcl,"待更新的权限点不存在");
        SysAcl after  = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId())
                    .url(param.getUrl()).type(param.getType()).status(param.getStatus())
                    .seq(param.getSeq()).remark(param.getRemark()).build();
            after.setOperator(RequestHolder.getCurrentUser().getUsername());
            after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            after.setOperateTime(new Date());
            this.sysAclMapper.updateByPrimaryKeySelective(after);
            this.sysLogServiceImpl.saveAclLog(sysAcl,after);
    }

    @Override
    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery query) {
        BeanValidator.check(query);
        int count = this.sysAclMapper.countByAclModuleId(aclModuleId);
        if(count>0){
            List<SysAcl> list = this.sysAclMapper.getPageByAclModuleId(aclModuleId,query);
            return PageResult.<SysAcl>builder().total(count).data(list).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    /**
     * 校验同一权限模块是否存在相同的权限点
     */
    public boolean checkExist(Integer aclModuleId, String name,Integer id){
        return this.sysAclMapper.countByNameAndAclModuleId(aclModuleId,name,id)>0;
    }

    public String genericCode(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date())+"_"+(int)(Math.random()*100);
    }
}
