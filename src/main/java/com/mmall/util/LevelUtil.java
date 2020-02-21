package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 计算层级
 */
public class LevelUtil {

    public static final String SEPARATOR = ".";

    public static final String ROOT = "0";
    // 0
    // 0.1
    // 0.1.1
    // 0.1.2
    public static String calculateLevel(String parentLevel,int parentId){
        if(StringUtils.isBlank(parentLevel)){
            return ROOT;
        }
        return  StringUtils.join(parentLevel,SEPARATOR,parentId);
    }
}
