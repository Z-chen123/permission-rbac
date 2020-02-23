package com.mmall.beans;


import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    //邮件主题
    private String subject;

    //邮件的信息
    private String message;

    //邮件收件人
    private Set<String> receivers;
}
