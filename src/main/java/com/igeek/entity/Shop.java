package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

	private Long shopId;
	private String shopName;
	private String shopDesc;
	private String shopAddr;
	private String phone;

	private String shopImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;//-1.不可用 0.审核中 1.可用

	private String advice;//超级管理员给店家的提醒
	private Area area;
	private PersonInfo owner;
	private ShopCategory shopCategory;
}
