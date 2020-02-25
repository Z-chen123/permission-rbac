package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.DeptLevelDto;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.service.impl.SysDeptTreeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptServiceImpl;

    @Resource
    private SysDeptTreeServiceImpl sysDeptTreeService;

    /**
     *  进入部门页面
     */
    @RequestMapping("/dept.page")
    @ResponseBody
    public ModelAndView page(){
        return new ModelAndView("dept");
    }

    /**
     * 添加部门
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param){
        sysDeptServiceImpl.saveDept(param);
        return JsonData.success();
    }

    /**
     * 更新部门
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param){
        sysDeptServiceImpl.updateDept(param);
        return JsonData.success();
    }

    /**
     * 获取部门树
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> dtoList = sysDeptTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 删除部门
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id")Integer id){
        this.sysDeptServiceImpl.delete(id);
        return JsonData.success();
    }
}
