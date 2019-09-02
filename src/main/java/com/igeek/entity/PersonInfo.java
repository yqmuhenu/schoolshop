package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfo implements Serializable {
    private Long userId;
    private String name;
    private String profileImg;
    private String email;
    private String gender;

    private Integer enableStatus;//用户状态
    private Integer userType;//1.顾客 2.店家 3.超级管理员
    private Date createTime;
    private Date lastEditTime;
}
