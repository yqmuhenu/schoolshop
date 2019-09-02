package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area implements Serializable {
    private Integer areaId;//ID
    private String areaName;//名称
    private Integer priority;//权重
    private Date createTime;//创建时间
    private Date lastEditTime;//更新时间
}
