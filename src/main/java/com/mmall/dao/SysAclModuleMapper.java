package com.mmall.dao;

import com.mmall.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameAndParentId(@Param("parentId") Integer parentId,@Param("aclModuleName") String aclModuleName,@Param("aclModuleId") Integer aclModuleId);

    List<SysAclModule> getChildAclModuleLevelByLevel(@Param("level") String level);

    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    List<SysAclModule> getAllAclModule();

    int countByParentId(@Param("id") Integer id);

}