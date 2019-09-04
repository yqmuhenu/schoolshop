package com.igeek.service;

import com.igeek.dto.ShopExecution;
import com.igeek.entity.Shop;
import com.igeek.exceptions.ShopOperationException;

import java.io.InputStream;

public interface ShopService {

    /**
     * 添加店铺
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, InputStream shopImg,String originalFileName) throws ShopOperationException;

    /**
     * 根据 shopId 查询指定店铺信息
     *
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息,包括对图片的处理
     * @param shop
     * @param shopImg
     * @param originalFileName
     * @return
     * @throws RuntimeException
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImg,String originalFileName) throws ShopOperationException;

}
