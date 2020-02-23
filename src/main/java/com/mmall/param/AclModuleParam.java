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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限模块名称不能为空")
    @Length(min=2,max=20,message="权限模块名称长度需要2-20字之间")
    private String name;

    private Integer parentId=0;

    @NotNull(message = "权限模块的顺序不能为空")
    private Integer seq;

    @NotNull(message ="权限模块状态不能为空" )
    @Min(value = 0,message = "权限模块状态不合法")
    @Max(value = 2,message = "权限模块状态不合法")
    private Integer status;

    @Length(max=200,message = "权限模块备注长度不能超过200")
    private String remark;
}
