package com.mmall.dao;

import com.mmall.model.SysAcl;
import com.mmall.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> getAclListByRoleIdList(@Param("userRoleIdList") List<Integer> userRoleIdList);

    void deleteByRoleId(@Param("roleId") int roleId);

    void batchInsert(@Param("sysRoleAcls") List<SysRoleAcl> sysRoleAcls);

    List<Integer> getRoleIdListByAclId(@Param("aclId") Integer aclId);
}