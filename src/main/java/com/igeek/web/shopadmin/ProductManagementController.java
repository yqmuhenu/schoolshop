package com.igeek.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.igeek.dto.ImageHolder;
import com.igeek.dto.ProductExecution;
import com.igeek.entity.Product;
import com.igeek.entity.ProductCategory;
import com.igeek.entity.Shop;
import com.igeek.enums.ProductStateEnum;
import com.igeek.exceptions.ProductOperationException;
import com.igeek.exceptions.ShopOperationException;
import com.igeek.service.ProductCategoryService;
import com.igeek.service.ProductService;
import com.igeek.util.CodeUtil;
import com.igeek.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/product")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    /**
     * 添加商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {

        //System.out.println("调用了addproduct!!!");

        Map<String, Object> modelMap = new HashMap<String, Object>();
        //1.验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //2.接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            //若请求中存在文件流，则取出相关的文件
            if (multipartResolver.isMultipart(request)) {
                //处理商品缩略图及详情图列表
                thumbnail = handleImage((MultipartHttpServletRequest)request,productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            //尝试获取前端传过来的表单String流并将其转换成Product实体类
            String productStr = HttpServletRequestUtil.getString(request,
                    "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        System.out.println("product : " + product);
        System.out.println("thumbnail : " + thumbnail);
        System.out.println("productImgList : " + productImgList);

        //3.若product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                //从session中获取当前店铺并赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                product.setShop(currentShop);

                //执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);

                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 根据商品id查询指定商品
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //非空判断
        if (productId > -1) {
            //获取要当前编辑的商品
            Product product = productService.getProductById(productId);
            //获取当前商店下的所有商品类别（供店家修改商品信息时选择）
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 商品编辑(更新商品信息)
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //1.判断是商品编辑、还是上/下架、店家删除商品操作（statusChange为true时为上/下架、店家删除商品操作）
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        //若为商品编辑则进行验证码判断，否则跳过验证码判断
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //2.接受前端参数的变量初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        //若请求中存在文件流，则取出相关的文件（包括详情图和缩略图）
        try {
            //System.out.println("thumbnail : " + thumbnail);
            //System.out.println("productImgList : " + productImgList);
            //System.out.println("productStr : " + productStr);
            //System.out.println("product : " + product);
            if (multipartResolver.isMultipart(request)) {
                //处理缩略图和详情图列表
                thumbnail = handleImage((MultipartHttpServletRequest) request,
                        productImgList);
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        //尝试获取前端传过来的表单String流并将其转换成Product实体类
        String productStr = HttpServletRequestUtil.getString(request,
                "productStr");
        try {
            product = mapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*System.out.println("thumbnail : " + thumbnail);
        System.out.println("productImgList : " + productImgList);
        System.out.println("productStr : " + productStr);
        System.out.println("product : " + product);*/

        //3.若商品信息不为空，则进行更新操作
        if (product != null) {
            try {
                //从session中获取当前店铺并赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                product.setShop(currentShop);

                //进行更新操作
                ProductExecution pe = productService.modifyProduct(product,
                        thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 处理添加商品及编辑商品时的缩略图、商品详情图列表
     * @param request
     * @param productImgList
     * @return
     * @throws IOException
     */
    private ImageHolder handleImage(MultipartHttpServletRequest request,
                                       List<ImageHolder> productImgList)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = request;
        ImageHolder thumbnail = null;

        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
                .getFile("thumbnail");
        if (thumbnailFile != null){
            System.out.println("productmanagementController : 添加了缩略图！！！");
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),
                    thumbnailFile.getInputStream());
        }

        //取出商品详情图列表并添加至productImgList,最多6张
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                    .getFile("productImg" + i);
            if (productImg != null) {
                System.out.println("productmanagementController : 添加了第" + (i+1) + "个商品详情图！！！");
                //若取出的第i个详情图文件流不为空
                productImgList.add(new ImageHolder(productImg.getOriginalFilename(),
                        productImg.getInputStream()));
            }else {
                //若取出的第i个详情图文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }

    /**
     * 获取当前店铺下的商品列表（分页）
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取前台传过来的页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取前台传过来的每页商品个数（上限）
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//获取当前店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		//空值判断
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
		    /**
             *获取传入的查询条件（包括商品类别，商品名模糊查询等）
             */
		    //商品类别id
		    long productCategoryId = HttpServletRequestUtil.getLong(request,
					"productCategoryId");
			//商品名
		    String productName = HttpServletRequestUtil.getString(request,
					"productName");
			//对查询条件排列组合（进行绑定，包括店铺id,商品类别id,商品名）
		    Product productCondition = compactProductCondition(
					currentShop.getShopId(), productCategoryId, productName);
			//调用service层方法查询商品列表
		    ProductExecution productExecution = productService.getProductList(
					productCondition, pageIndex, pageSize);
			modelMap.put("productList", productExecution.getProductList());
			modelMap.put("count", productExecution.getCount());
			modelMap.put("success", true);
		} else{
            modelMap.put("success", false);
		    if (!(pageIndex > -1)){
                modelMap.put("errMsg", "查询页数不存在！");
            } else if (!(pageSize > -1)){
                modelMap.put("errMsg", "要查询的商品列表不存在！");
            } else {
                modelMap.put("errMsg", "店铺不存在！");
            }
        }
		return modelMap;
	}

    /**
     * 将商品列表查询条件与productCondition进行绑定
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
	private Product compactProductCondition(long shopId,
                                            long productCategoryId,
                                            String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}
