package com.igeek.service;

import java.io.InputStream;
import java.util.List;

import com.igeek.dto.ImageHolder;
import com.igeek.dto.ProductExecution;
import com.igeek.entity.Product;
import com.igeek.exceptions.ProductOperationException;

public interface ProductService {
    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺id，商品类别
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 通过商品id查询唯一的商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 添加商品信息以及图片处理
     *
     * @param product        商品
     * @param thumbnail      缩略图
     * @param productImgList 详情图片列表
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail,
                                List<ImageHolder> productImgList)
            throws ProductOperationException;

    /**
     * 更新商品信息以及图片处理
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws RuntimeException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgList)
            throws ProductOperationException;

    /**
     * 删除商品，同时删除商品缩略图，商品详情图（图片处理）
     * @param product
     * @return
     * @throws ProductOperationException
     */
    /*ProductExecution deleteProduct(Product product)
            throws ProductOperationException;*/
}
