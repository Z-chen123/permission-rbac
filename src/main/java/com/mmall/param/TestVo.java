package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class TestVo {


    @NotNull(message = "id 不能为空")
    @Min(value=1,message = "最小值不能小于1")
    @Max(value=10,message = "最大不能大于10")
    private Integer id;

    @NotBlank
    private String name;

    private List<String> str;
}
