package com.igeek.dao;

import com.igeek.BaseTest;
import com.igeek.entity.Area;
import com.igeek.entity.PersonInfo;
import com.igeek.entity.Shop;
import com.igeek.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    //@Ignore
    public void testInsertShop(){
        Shop shop = new Shop();

        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        shop.setShopName("测试的店铺");
        shop.setShopDesc("测试");
        shop.setShopAddr("测试");
        shop.setPhone("测试");

        shop.setShopImg("测试");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(38L);

        Area area = new Area();
        area.setAreaId(1);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);

        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        shop.setShopDesc("测试的描述");
        shop.setShopAddr("测试的地址");

        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }
}
