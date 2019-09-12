package com.igeek.service;

import com.igeek.BaseTest;
import com.igeek.dto.ImageHolder;
import com.igeek.dto.ProductExecution;
import com.igeek.dto.ShopExecution;
import com.igeek.entity.*;
import com.igeek.enums.ProductStateEnum;
import com.igeek.enums.ShopStateEnum;
import com.igeek.exceptions.ProductOperationException;
import com.igeek.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws ShopOperationException,FileNotFoundException {
        //创建shopId为1且productCategoryId为1的商品实例(product)并给其成员变量赋值
        Shop shop = new Shop();
        shop.setShopId(1L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);

        Product product = new Product();
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setProductCategory(productCategory);
        product.setShop(shop);

        //创建缩略图文件流
        File shopImg = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        ImageHolder thumbnail = new ImageHolder(shopImg.getName(),new FileInputStream(shopImg));
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        File productImg2 = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),new FileInputStream(productImg1)));
        productImgList.add(new ImageHolder(productImg2.getName(),new FileInputStream(productImg2)));

        ProductExecution productExecution = productService.addProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
    }

    //@Ignore
    /*@Test
    public void testGetByShopId() throws Exception {
        long shopId = 1;
        Shop shop = shopService.getByShopId(shopId);
        System.out.println(shop);
    }*/

    @Test
    public void testModifyProduct() throws ProductOperationException,FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);

        Product product = new Product();
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductId(1L);
        product.setProductName("更新的第一个商品");
        product.setProductDesc("更新的第一个商品");

        //创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),new FileInputStream(thumbnailFile));
        //创建商品详情图文件流
        List<ImageHolder> productImgList = new ArrayList<>();
        File productImg1 = new File("C:\\Users\\30624\\Pictures\\WallPapers\\jpg/01.jpg");
        File productImg2 = new File("C:\\Users\\30624\\Pictures\\WallPapers\\jpg/02.jpg");
        productImgList.add(new ImageHolder(productImg1.getName(),new FileInputStream(productImg1)));
        productImgList.add(new ImageHolder(productImg2.getName(),new FileInputStream(productImg2)));
        //添加商品并验证
        ProductExecution productExecution = productService.modifyProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
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
