package com.igeek.dao;

import com.igeek.BaseTest;
import com.igeek.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    //@Ignore
    public void testBQueryProductCategoryList(){
        long shopId = 1;
        List<ProductCategory> productCategoryList;

        productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        assertEquals(7,productCategoryList.size());
        System.out.println(productCategoryList);
    }

    @Test
    public void testABatchInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testCDeleteProductCategory() {
        long shopId = 1;
        List<ProductCategory> productCategoryList;
        productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for(ProductCategory productCategory : productCategoryList){
            if("商品类别1".equals(productCategory.getProductCategoryName())
                || "商品类别2".equals(productCategory.getProductCategoryName())){
                int effectedNum = productCategoryDao.deleteProductCategory(
                        productCategory.getProductCategoryId(),shopId);
                assertEquals(1,effectedNum);
            }
        }
    }
}
