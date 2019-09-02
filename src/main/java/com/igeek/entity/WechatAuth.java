package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatAuth implements Serializable {
    private Long wechatAuthId;
    private String openId;
    private Date createTime;
    private PersonInfo personInfo;
}
