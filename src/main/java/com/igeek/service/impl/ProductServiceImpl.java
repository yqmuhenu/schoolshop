package com.igeek.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.igeek.dao.ProductDao;
import com.igeek.dao.ProductImgDao;
import com.igeek.dto.ImageHolder;
import com.igeek.dto.ProductExecution;
import com.igeek.entity.Product;
import com.igeek.entity.ProductImg;
import com.igeek.enums.ProductStateEnum;
import com.igeek.exceptions.ProductOperationException;
import com.igeek.service.ProductService;
import com.igeek.util.ImageUtil;
import com.igeek.util.PageCalculator;
import com.igeek.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		//页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		//基于同样的查询条件返回商品总数
		int count = productDao.queryProductCount(productCondition);
		ProductExecution productExecution = new ProductExecution();
		productExecution.setProductList(productList);
		productExecution.setCount(count);
		return productExecution;
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	/**
	 * 1.处理缩略图，获取缩略图相对路径并赋值给product
	 * 2.往tb_product写入商品信息，获取productId
	 * 3.结合productId批量处理商品详情图
	 * 4.将商品详情图列表批量插入tb_product_img中
	 */
	public ProductExecution addProduct(Product product, ImageHolder thumbnail,
									   List<ImageHolder> productImgList)
			throws ProductOperationException {

		System.out.println("-----------ProductService_addProduct--------------");
		System.out.println("product : " + product);
		System.out.println("thumbnail : " + thumbnail);
		System.out.println("productImgList : " + productImgList);
		//空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//给商品设置上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认为上架的状态
			product.setEnableStatus(1);
			//若商品缩略图不为空则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				//创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败:" + e.toString());
			}
			//若商品详情列表不为空则添加
			if (productImgList != null && productImgList.size() > 0) {
				addProductImgList(product, productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			//传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	/**
	 * 实现三个功能：商品信息更新，上/下架，店家删除商品（伪删除）
	 * 1.若缩略图有值，则处理缩略图，
	 * 若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	 * 2.若商品详情图列表参数有值，对商品详情图列表进行同样的操作
	 * 3.将tb_product_img下面的该商品原先的商品详情图记录全部清除
	 * 4.更新tb_product的信息
	 */
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
										  List<ImageHolder> productImgList)
			throws ProductOperationException {

		System.out.println("-----------ProductService_modifyProduct--------------");
		System.out.println("product : " + product);
		System.out.println("thumbnail : " + thumbnail);
		System.out.println("productImgList : " + productImgList);

		//空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//获取原有信息
			Product tempProduct = productDao.queryProductById(product.getProductId());

			//判断是否可以进行此次更新（若是店家删除商品，判断商品是否已经下架）
			//若商品未下架则不可删除（即本次更新取消）
			if (product.getEnableStatus() == -1 &&
					tempProduct.getEnableStatus() == 1){
				throw new ProductOperationException("删除失败，商品下架后才可删除！");
			}

			//给商品设置上默认属性
			product.setLastEditTime(new Date());
			//若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if (thumbnail != null) {
				//根据原有缩略图路径删除原有缩略图
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				//添加当前缩略图
				addThumbnail(product, thumbnail);
			}

			try {
				//如果有新存入的商品详情图，则将原先的删除，并添加新的图片
				if (productImgList != null && productImgList.size() > 0) {
					//删除商品详情图列表失败时会抛出异常
					deleteProductImgList(product.getProductId());
					addProductImgList(product, productImgList);
				}
				//更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}


	/**
	 * 删除商品记录(供管理员使用)
	 * 1.删除商品缩略图文件，详情图文件，
	 * 2.删除商品详情图记录，商品记录
	 * @param product
	 * @return
	 * @throws ProductOperationException
	 */
	/*@Override
	@Transactional
	public ProductExecution deleteProduct(Product product)
			throws ProductOperationException {
		System.out.println("-----------ProductService_deleteProduct--------------");
		System.out.println("product : " + product);

		//空值判断
		if (product != null && product.getProductId() != null &&
				product.getShop() != null && product.getShop().getShopId() != null) {
			//获取原有商品信息
			Product tempProduct = productDao.queryProductById(product.getProductId());
			//判断商品是否不可用（enableStatus为-1），否则不可删除
			if(tempProduct.getEnableStatus() != 0){
				throw new ProductOperationException("删除商品信息失败:该商品店家尚未删除，店家删除后方可彻底删除！");
			}
			//根据原有缩略图路径删除原有缩略图
			if (tempProduct.getImgAddr() != null) {
				ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
			}

			try {
				//删除商品详情图列表
				deleteProductImgList(product.getProductId());
				//删除商品记录
				int effectedNum = productDao.deleteProduct(product.getProductId(),
						product.getShop().getShopId());
				if (effectedNum <= 0) {
					throw new ProductOperationException("删除商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("删除商品信息失败:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}*/


	/**
	 * 为商品product添加缩略图
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		//图片相对路径目录
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		//图片相对路径目录+随机名+扩展名
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		//为商品product设置缩略图属性（路径）
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * 批量添加图片（商品详情列表）批量添加图片（商品详情列表）
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		//获取图片存储路径，这里直接存放到相应店铺的文件夹底下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		//遍历图片依次去处理，并添加进productImg实体类中
		for (ImageHolder productImageHolder : productImgHolderList){
			String imgAddr = ImageUtil.generateNormalImg(productImageHolder,dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		//如果确实是有图片需要添加的，就执行批量添加操作
		if(productImgList.size() > 0){
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
			}
		}
	}

	/**
	 * 根据商品id删除商品详情图列表(供更新商品信息时调用)
	 * @param productId
	 */
	private void deleteProductImgList(long productId) {
		//从数据库获取商品详情图列表
		List<ProductImg> productImgList = productImgDao.queryProductImgListByProductId(productId);
		//从文件夹中删除商品详情图列表
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		//从数据库中删除商品详情图列表
		int efffectedNum = productImgDao.deleteProductImgByProductId(productId);
		if (efffectedNum <= 0){
			throw new ProductOperationException("删除原有商品详情图列表失败！");
		}
	}



	/*@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {

	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductByProductId(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws RuntimeException {

	}*/

	/*



	*/
}
