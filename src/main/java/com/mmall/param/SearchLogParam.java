package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchLogParam {
    private Integer type;

    private String beforeSeq;

    private String afterSeq;

    private String operator;

    private String fromTime; //yyyy-MM-dd HH:ss:mm

    private String toTime;
}
