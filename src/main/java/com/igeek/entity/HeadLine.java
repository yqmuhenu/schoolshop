package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeadLine {
	private Long lineId;
	private String lineName;
	private String lineLink;
	private String lineImg;
	private Integer priority;
	private Integer enableStatus;//0:不可用 1.可用
	private Date createTime;
	private Date lastEditTime;
}
