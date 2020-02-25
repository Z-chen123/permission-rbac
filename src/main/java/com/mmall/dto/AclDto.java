package com.mmall.dto;

import com.mmall.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
@ToString
public class AclDto extends SysAcl {

    //权限点是否默认选中
    private boolean checked = false;

    //是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adpt(SysAcl sysAcl){
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(sysAcl,dto);
        return dto;
    }

}
