package com.igeek.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.igeek.BaseTest;
import com.igeek.entity.Product;
import com.igeek.entity.ProductCategory;
import com.igeek.entity.ProductImg;
import com.igeek.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testAInsertProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		Shop shop2 = new Shop();
		shop2.setShopId(14L);

		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(2L);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryId(3L);
		ProductCategory pc3 = new ProductCategory();
		pc3.setProductCategoryId(11L);

		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(0);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);

		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(0);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc2);

		Product product3 = new Product();
		product3.setProductName("测试3");
		product3.setProductDesc("测试Desc3");
		product3.setImgAddr("test3");
		product3.setPriority(0);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop2);
		product3.setProductCategory(pc3);

		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product3);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryProductList() throws Exception {
		Product productCondition = new Product();
		List<Product> productList = productDao.queryProductList(productCondition,
				0, 3);
		assertEquals(3, productList.size());

		productCondition.setProductName("测试");
		productList = productDao.queryProductList(productCondition, 0, 7);
		assertEquals(6, productList.size());


		Shop shop = new Shop();
		shop.setShopId(14L);
		productCondition.setShop(shop);
		productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(1, productList.size());
	}

	@Test
	public void testBQueryProductCount() throws Exception {
		Product productCondition = new Product();
		int count;

		count = productDao.queryProductCount(productCondition);
		assertEquals(12, count);

		productCondition.setProductName("测试");
		count = productDao.queryProductCount(productCondition);
		assertEquals(6, count);

		Shop shop = new Shop();
		shop.setShopId(14L);
		productCondition.setShop(shop);
		count = productDao.queryProductCount(productCondition);
		assertEquals(1, count);
	}

	@Test
	public void testCQueryProductById() throws Exception {
		long productId = 1;
		Product product;
		product = productDao.queryProductById(productId);
		assertEquals(2, product.getProductImgList().size());
		System.out.println(product);
	}

	@Test
	public void testDUpdateProduct() throws Exception {
		Shop shop = new Shop();
		shop.setShopId(1L);
		Product product = new Product();
		product.setProductId(1L);
		product.setShop(shop);

		product.setProductName("第一个产品");
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}

	//@Ignore
	@Test
	public void testUpdateProductCategoryToNull() throws Exception {
		long productCategoryId = 3;
		int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
		assertEquals(1, effectedNum);
	}
}
