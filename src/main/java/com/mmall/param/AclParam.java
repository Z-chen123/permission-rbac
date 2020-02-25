package com.mmall.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class AclParam {

    private Integer id;

    @NotBlank(message = "权限点名称不能为空")
    @Length(min =2,max=20,message = "权限点名称长度在2-64字符之间")
    private String name;

    @NotNull(message = "必须指定权限模块")
    private Integer aclModuleId;

    @Length(min=6,max=100,message = "权限点url长度在6-100字符之间")
    private String url;

    @NotNull(message = "必须指定权限点类型")
    @Min(value=0,message = "权限点类型不合法")
    @Max(value = 3,message="权限点类型不合法")
    private Integer type;

    @NotNull(message = "必须指定权限点状态")
    @Min(value=0,message = "权限点状态不合法")
    @Max(value = 1,message="权限点状态不合法")
    private Integer status;

    @NotNull(message = "必须指定权限点的展示顺序")
    private Integer seq;

    @Length(min=0,max=200,message="权限点备注长度在0-200字符之间")
    private String remark;
}
