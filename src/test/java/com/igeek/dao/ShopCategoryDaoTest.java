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

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory(){
        ShopCategory parentShopCategory = new ShopCategory();
        parentShopCategory.setShopCategoryId(1L);

        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setParent(parentShopCategory);

        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
        assertEquals(1,shopCategoryList.size());
        System.out.println(shopCategoryList.size());
        System.out.println(shopCategoryList);
    }

    @Test
    public void testQueryShopCategoryById(){
        int id = 1;
        ShopCategory shopCategory = shopCategoryDao.queryShopCategoryById(id);
        System.out.println(shopCategory);
    }

}
