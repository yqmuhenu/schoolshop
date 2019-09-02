package com.igeek.service;

import com.igeek.BaseTest;
import com.igeek.dto.ShopExecution;
import com.igeek.entity.Area;
import com.igeek.entity.PersonInfo;
import com.igeek.entity.Shop;
import com.igeek.entity.ShopCategory;
import com.igeek.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() throws IOException {
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
        InputStream in = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop,in,shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }


	/*@Test
	public void testGetByEmployeeId() throws Exception {
		long employeeId = 1;
		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Ignore
	@Test
	public void testGetByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopService.getByShopId(shopId);
		System.out.println(shop);
	}*/

}
