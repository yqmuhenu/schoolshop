package com.igeek.service;

import com.igeek.BaseTest;
import com.igeek.dto.ImageHolder;
import com.igeek.dto.ShopExecution;
import com.igeek.entity.Area;
import com.igeek.entity.PersonInfo;
import com.igeek.entity.Shop;
import com.igeek.entity.ShopCategory;
import com.igeek.enums.ShopStateEnum;
import com.igeek.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testQueryShopList() throws Exception {
        Shop shopCondition = new Shop();
        ShopExecution shopExecution;

        shopExecution = shopService.getShopList(shopCondition, 0, 2);
        assertEquals(2, shopExecution.getShopList().size());
        assertEquals(16, shopExecution.getCount());

        shopCondition.setShopName("测试");
        shopExecution = shopService.getShopList(shopCondition, 0, 3);
        assertEquals(3, shopExecution.getShopList().size());
        assertEquals(9, shopExecution.getCount());

        shopCondition.setShopId(1L);
        shopExecution = shopService.getShopList(shopCondition, 0, 3);
        assertEquals(0, shopExecution.getShopList().size());
        assertEquals(0, shopExecution.getCount());

    }

    @Test
    public void testAddShop() throws ShopOperationException,FileNotFoundException {
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

        shop.setShopName("测试的店铺1");
        shop.setShopDesc("测试1");
        shop.setShopAddr("测试1");
        shop.setPhone("测试1");

        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File shopImg = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),new FileInputStream(shopImg));
        ShopExecution shopExecution = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }

    //@Ignore
    @Test
    public void testGetByShopId() throws Exception {
        long shopId = 1;
        Shop shop = shopService.getByShopId(shopId);
        System.out.println(shop);
    }

    @Test
    public void testModifyShop() throws ShopOperationException,FileNotFoundException {
        Shop shop = shopService.getByShopId(1L);

        shop.setShopName("更新的店铺1");
        shop.setLastEditTime(new Date());

        File shopImg = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),new FileInputStream(shopImg));

        ShopExecution shopExecution = shopService.modifyShop(shop,imageHolder);
        //assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
        System.out.println(shopExecution.getShop().getShopImg());
    }

	/*
	@Test
	public void testGetByEmployeeId() throws Exception {
		long employeeId = 1;
		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}
	*/

}
