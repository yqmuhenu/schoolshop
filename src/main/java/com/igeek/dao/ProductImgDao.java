package com.igeek.dao;

import java.util.List;

import com.igeek.entity.ProductImg;

public interface ProductImgDao {

	/**
	 * 根据商品id查询商品图片列表
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgListByProductId(long productId);

	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	/**
	 * 根据商品id删除商品图片
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
