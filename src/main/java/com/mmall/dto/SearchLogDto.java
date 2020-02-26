package com.mmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class SearchLogDto {

    private Integer type; //LogType

    private String beforeSeq;

    private String afterSeq;

    private String operator;

    private Date fromTime; //yyyy-MM-dd HH:ss:mm

    private Date toTime;  //yyyy-MM-dd HH:ss:mm
}
