package com.mmall.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.AclDto;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclModule;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDeptTreeServiceImpl {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysCoreServiceImpl sysCoreServiceImpl;

    @Resource
    private SysAclMapper sysAclMapper;

    public List<AclModuleLevelDto> userAclTree(int userId){
        List<SysAcl> sysAclList = this.sysCoreServiceImpl.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        for(SysAcl acl : sysAclList){
            AclDto dto = AclDto.adpt(acl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }
    public List<AclModuleLevelDto> roleTree(int roleId){
        //1.当前用户已经分配的权限
        List<SysAcl> userAclList = this.sysCoreServiceImpl.getCurrentUserAclList();
        //2.当前角色已经分配的权限
        List<SysAcl> roleAclList = this.sysCoreServiceImpl.getRoleAclList(roleId);

        Set<Integer> userAclIdList = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdList = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        //获取系所有权限点
        List<SysAcl> sysAclList = this.sysAclMapper.getAll();
/*        Set<SysAcl> aclSet = new HashSet<>(roleAclList);
        aclSet.addAll(userAclList);*/
//        Set<SysAcl> aclSet = new HashSet<>(sysAclList);

        List<AclDto> aclDtoList = Lists.newArrayList();
        for(SysAcl acl : sysAclList){
            AclDto dto = AclDto.adpt(acl);
            if(userAclIdList.contains(acl.getId())){
               dto.setHasAcl(true);
            }
            if(roleAclIdList.contains(acl.getId())){
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    /**
     * 获取权限点树结构
     * @param aclDtoList
     * @return
     */
    private List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if(CollectionUtils.isEmpty(aclDtoList)){
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();
        Multimap<Integer,AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for(AclDto aclDto :aclDtoList){
            if(aclDto.getStatus()==1){
            moduleIdAclMap.put(aclDto.getAclModuleId(),aclDto);
            }
        }
        bindAclsWithOrder(aclModuleLevelList,moduleIdAclMap);
        return  aclModuleLevelList;
    }

    /**
     * 权限点绑定到权限树上
     * @param aclModuleLevelList
     * @param moduleIdAclMap
     * @return
     */
    private void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if(CollectionUtils.isEmpty(aclModuleLevelList)){
            return ;
        }
        for(AclModuleLevelDto aclModuleLevelDto:aclModuleLevelList){
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(aclModuleLevelDto.getId());
            if(CollectionUtils.isNotEmpty(aclDtoList)){
                Collections.sort(aclDtoList,aclSeqComparator);
                aclModuleLevelDto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(aclModuleLevelDto.getAclModuleList(),moduleIdAclMap);
        }

    }

    public List<AclModuleLevelDto> aclModuleTree(){
       List<SysAclModule> aclModuleList = this.sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for(SysAclModule aclModule : aclModuleList){
            AclModuleLevelDto dto = AclModuleLevelDto.adapt(aclModule);
            dtoList.add(dto);
        }
        return  aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelList) {
        if(CollectionUtils.isEmpty(aclModuleLevelList)){
            return Lists.newArrayList();
        }
        // google 工具类（level -> [aclModule1,aclModule2,aclModule3...]）相当于 Map<String,List<Object>>结构
        Multimap<String,AclModuleLevelDto> map = ArrayListMultimap.create();

        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        for(AclModuleLevelDto dto : aclModuleLevelList){
            map.put(dto.getLevel(),dto);
            if(LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<AclModuleLevelDto>() {
            @Override
            public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
                return o1.getSeq()-o2.getSeq();
            }
        });
        //递归生成树
        tranformAclModuleTree(rootList,LevelUtil.ROOT,map);
        return rootList;
    }

    private void tranformAclModuleTree(List<AclModuleLevelDto> aclModuleLevelList, String level, Multimap<String, AclModuleLevelDto> map) {
        for(int i =0; i<aclModuleLevelList.size();i++){
            //遍历该层的每个元素
            AclModuleLevelDto dto = aclModuleLevelList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            //处理下一层
            List<AclModuleLevelDto> tempDeptList = (List<AclModuleLevelDto>) map.get(nextLevel);

            if(CollectionUtils.isNotEmpty(tempDeptList)){
                //排序
                Collections.sort(tempDeptList,aclModuleSeqComparator);
                //设置下一层
                dto.setAclModuleList(tempDeptList);
                //进入下一层处理
                tranformAclModuleTree(tempDeptList,nextLevel,map);
            }
        }
    }

    public List<DeptLevelDto> deptTree(){
        List<SysDept> deptList = this.sysDeptMapper.getAllDept();
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for(SysDept dept : deptList){
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    /**
     *  将集合转成树形结构
     * @param deptLevelList
     * @return
     */
    public List<DeptLevelDto> deptListToTree( List<DeptLevelDto> deptLevelList){
        if(CollectionUtils.isEmpty(deptLevelList)){
            return Lists.newArrayList();
        }
        // google 工具类（level -> [dept1,dept2,dept3...]）相当于 Map<String,List<Object>>结构
        Multimap<String,DeptLevelDto> map = ArrayListMultimap.create();

        List<DeptLevelDto> rootList = Lists.newArrayList();
        for(DeptLevelDto dto : deptLevelList){
            map.put(dto.getLevel(),dto);
            if(LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq()-o2.getSeq();
            }
        });
        //递归生成树
        tranformDeptTree(rootList,LevelUtil.ROOT,map);
        return rootList;
    }

    /**
     * 从根节点递归生成树
     * @param deptLevelList
     * @param level
     * @param map
     */
    public void tranformDeptTree(List<DeptLevelDto> deptLevelList,String level,Multimap<String,DeptLevelDto> map){
        for(int i =0; i<deptLevelList.size();i++){
            //遍历该层的每个元素
            DeptLevelDto dto = deptLevelList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) map.get(nextLevel);

            if(CollectionUtils.isNotEmpty(tempDeptList)){
                //排序
                Collections.sort(tempDeptList,deptSeqComparator);
                //设置下一层
                dto.setDeptList(tempDeptList);
                //进入下一层处理
                tranformDeptTree(tempDeptList,nextLevel,map);
            }
        }

    }

    /**
     * 排序
     */
    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
                @Override
                public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                    return o1.getSeq()-o2.getSeq();
                }
            };

    /**
     * 排序
     */
    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };

    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };
}
