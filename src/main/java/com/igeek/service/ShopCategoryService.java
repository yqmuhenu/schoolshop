package com.igeek.service;

import com.igeek.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    /**
     * 获取店铺类别列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
