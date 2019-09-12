package com.igeek.dto;

import com.igeek.entity.ProductCategory;
import com.igeek.enums.ProductCategoryStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCategoryExecution {
	// 结果状态
	private int state;
	// 状态标识
	private String stateInfo;
	// 操作的商铺类别
	private List<ProductCategory> productCategoryList;

	/**
	 * 空参构造器
	 */
	public ProductCategoryExecution() {
	}

	/**
	 * 预约失败的构造器
	 * @param stateEnum
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	/**
	 * 预约成功的构造器
	 * @param stateEnum
	 * @param productCategoryList
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,
			List<ProductCategory> productCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
	}
}
