package com.mmall.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * json 返回 封装正常，异常返回结果
 */
@Setter
@Getter
public class JsonData {

    //返回结果
    private boolean ret;

    //异常信息
    private String msg;

    // 后台返回数据
    private Object data;

    public JsonData(boolean ret){
        this.ret = ret;
    }

    //成功
    public static JsonData success(Object object,String msg){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData success(){
      return new JsonData(true);
    }

    //失败
    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("ret",ret);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }

}
