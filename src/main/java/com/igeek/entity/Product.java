package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{
	
	private static final long serialVersionUID = -349433539553804024L;

	private Long productId;
	private String productName;
	private String productDesc;
	private String imgAddr;// 简略图
	private String normalPrice;//原价

	private String promotionPrice;//折扣价
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;//-1不可用,0.下架,1.在前端展示系统显示

	private List<ProductImg> productImgList;//商品详情列表
	private ProductCategory productCategory;//商品类别
	private Shop shop;//所属店铺（由哪个店铺发布）
}
