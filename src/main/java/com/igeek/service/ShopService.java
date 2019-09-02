package com.igeek.service;

import com.igeek.dto.ShopExecution;
import com.igeek.entity.Shop;

import java.io.InputStream;

public interface ShopService {

    /**
     * 添加店铺
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, InputStream shopImg,String originalFileName);
}
