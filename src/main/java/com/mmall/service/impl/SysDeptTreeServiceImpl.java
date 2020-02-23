package com.mmall.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysDeptTreeServiceImpl {
    @Resource
    private SysDeptMapper sysDeptMapper;

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
}
