package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.SysUserService;
import com.mmall.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public void saveUser(UserParam param) {
        BeanValidator.check(param);
        // 根据邮箱校验用户是否存在
        if(CheckEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }
        if(CheckTelephoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("手机号已被占用");
        }
//        String password="123456";
        //生成八位数的密码
        String password = PasswordUtil.createPassword(8);
        System.out.println("password = " + password);
        String encryptPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).password(encryptPassword).deptId(param.getDeptId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        //TODO sendMail
        EmailUtil.sendEmial(param.getMail(),"登录权限密码:"+password+",不是请勿回复","权限系统");
        sysUserMapper.insertSelective(user);
    }
    public void updateUser(UserParam param){
        BeanValidator.check(param);
        // 根据邮箱校验用户是否存在
        if(CheckEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }
        if(CheckTelephoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("手机号已被占用");
        }
        SysUser before = this.sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的用户不存在");

        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).deptId(param.getDeptId())
                .status(param.getStatus()).remark(param.getRemark()).build();
                after.setOperator(RequestHolder.getCurrentUser().getUsername());
                after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                after.setOperateTime(new Date());
                this.sysUserMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public SysUser findByKeyword(String username) {
        return this.sysUserMapper.findByKeyword(username);
    }

    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery query) {
        BeanValidator.check(query);
        int count = this.sysUserMapper.countByDeptId(deptId);
        if(count>0){
            List<SysUser> list = this.sysUserMapper.getPageByDeptId(deptId,query);
            return PageResult.<SysUser>builder().data(list).total(count).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    @Override
    public List<SysUser> getAll() {
        return this.sysUserMapper.getAll();
    }

    public boolean CheckEmailExist(String mail, Integer id) {
        return this.sysUserMapper.countByMail(mail,id)>0;
    }

    public boolean CheckTelephoneExist(String telephone, Integer id) {
        return this.sysUserMapper.countByTelephone(telephone,id)>0;
    }
}
