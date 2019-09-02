package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory implements Serializable {

	private Long productCategoryId;
	private Long shopId;
	private String productCategoryName;
	private Integer priority;
	private Date createTime;
}
