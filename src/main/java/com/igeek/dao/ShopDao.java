package com.igeek.dao;

import com.igeek.entity.Shop;

public interface ShopDao {

    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过 shopId 查询店铺
     *
     * @param shopId
     * @return shop
     */
    Shop queryByShopId(long shopId);
}
