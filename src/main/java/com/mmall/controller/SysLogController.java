package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysLog;
import com.mmall.model.SysLogWithBLOBs;
import com.mmall.param.SearchLogParam;
import com.mmall.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Resource
    private SysLogService sysLogServiceImpl;

    @RequestMapping("/log.page")
    @ResponseBody
    public ModelAndView page(){
        return new ModelAndView("log");
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchPage(SearchLogParam param, PageQuery page){
        PageResult<SysLogWithBLOBs> result = this.sysLogServiceImpl.searchPageList(param,page);
        return JsonData.success(result);
    }

    /**
     * 还原操作
     * @param id
     * @return
     */
    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(@RequestParam("id") Integer id){
         this.sysLogServiceImpl.recover(id);
        return JsonData.success();
    }
}
