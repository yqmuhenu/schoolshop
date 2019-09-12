package com.igeek.service;

import com.igeek.BaseTest;
import com.igeek.dao.ProductCategoryDao;
import com.igeek.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryServiceTest extends BaseTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void testQueryProductCategoryList(){
        long shopId = 1;
        List<ProductCategory> productCategoryList;

        productCategoryList = productCategoryService.getProductCategoryList(shopId);
        assertEquals(3,productCategoryList.size());
        System.out.println(productCategoryList);
    }

}
