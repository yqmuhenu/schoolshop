package com.igeek.dao;

import com.igeek.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    /**
     * 根据条件查询店铺类别
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);

    /**
     * 根据shopCategoryId店铺类别
     * @param shopCategoryId
     * @return
     */
    ShopCategory queryShopCategoryById(int shopCategoryId);
}
