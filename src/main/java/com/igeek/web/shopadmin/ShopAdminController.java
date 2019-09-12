package com.igeek.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {
    @RequestMapping(value = "/shoplist")
    public String shopList(){
        //转发至店铺列表页面
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopoperation")
    public String shopOperation(){
        //转发至店铺注册/编辑页面
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        //转发至店铺管理页面
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement")
    public String productCategoryManagement(){
        //转发至商品类别管理页面
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productmanagement")
    public String productManagement(){
        //转发至商品管理（列表）页面
        return "shop/productmanagement";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        //转发至商品添加/编辑页面
        return "shop/productoperation";
    }
}
