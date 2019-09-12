package com.igeek.dao;

import com.igeek.BaseTest;
import com.igeek.entity.Area;
import com.igeek.entity.PersonInfo;
import com.igeek.entity.Shop;
import com.igeek.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopList() throws Exception {
        Shop shopCondition = new Shop();
        List<Shop> shopList;
        int count;

        shopList = shopDao.queryShopList(shopCondition, 0, 2);
        assertEquals(2, shopList.size());
        count = shopDao.queryShopCount(shopCondition);
        assertEquals(16, count);

        shopCondition.setShopName("测试");
        shopList = shopDao.queryShopList(shopCondition, 0, 3);
        assertEquals(3, shopList.size());
        count = shopDao.queryShopCount(shopCondition);
        assertEquals(9, count);

        shopCondition.setShopId(1L);
        shopList = shopDao.queryShopList(shopCondition, 0, 3);
        assertEquals(1, shopList.size());
        count = shopDao.queryShopCount(shopCondition);
        assertEquals(1, count);

    }

    @Test
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop);
    }

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
