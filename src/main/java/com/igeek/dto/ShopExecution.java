package com.igeek.dto;

import com.igeek.entity.Shop;
import com.igeek.enums.ShopStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 封装执行后结果
 */
@Setter
@Getter
public class ShopExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop（增删改店铺的时候用）
	private Shop shop;

	// 获取的shop列表(查询店铺列表的时候用)
	private List<Shop> shopList;

	/**
	 * 默认构造器
	 */
	public ShopExecution() {
	}

	/**
	 * 店铺操作失败的时候使用的构造器
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	/**
	 * 店铺操作成功的时候使用的构造器(单个shop)
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}

	/**
	 * 店铺操作成功的时候使用的构造器(shop列表)
	 * @param stateEnum
	 * @param shopList
	 */
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}
}