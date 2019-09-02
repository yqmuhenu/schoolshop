package com.igeek.service.impl;

import com.igeek.dao.ShopDao;
import com.igeek.dto.ShopExecution;
import com.igeek.entity.Shop;
import com.igeek.enums.ShopStateEnum;
import com.igeek.exceptions.ShopOperationException;
import com.igeek.service.ShopService;
import com.igeek.util.ImageUtil;
import com.igeek.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    /**
     * 使用注解控制事务方法的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImg,String originalFileName)
            throws RuntimeException {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            //System.out.println(effectedNum);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (shopImg != null) {
                    //存储图片
                    try {
                        addShopImg(shop, shopImg,originalFileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error: "
                                + e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("创建图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("insertShop error: " + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    /**
     * 为shop添加图片
     * @param shop
     * @param shopImg
     * @param originalFileName
     */
    private void addShopImg(Shop shop, InputStream shopImg,String originalFileName) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest,originalFileName);
        //为shop设置图片(shopImg)
        shop.setShopImg(shopImgAddr);
    }

	/*@Autowired
	private ShopDao shopDao;
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex,
			int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex,
				pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		} else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

	@Override
	public ShopExecution getByEmployeeId(long employeeId)
			throws RuntimeException {
		List<Shop> shopList = shopDao.queryByEmployeeId(employeeId);
		ShopExecution se = new ShopExecution();
		se.setShopList(shopList);
		return se;
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}



	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg)
			throws RuntimeException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		} else {
			try {
				if (shopImg != null) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						FileUtil.deleteFile(tempShop.getShopImg());
					}
					addShopImg(shop, shopImg);
				}
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {// 创建成功
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}
			} catch (Exception e) {
				throw new RuntimeException("modifyShop error: "
						+ e.getMessage());
			}
		}
	}
    */

}
