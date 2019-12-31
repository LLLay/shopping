package com.tt.o2o.web.shopadmin;


import com.tt.o2o.dto.ProductCategoryExecution;
import com.tt.o2o.dto.Result;
import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.enums.ProductCategoryStateEnum;
import com.tt.o2o.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private IProductCategoryService iProductCategoryService;

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        // Map<String, Object> modelMap = new HashMap<>();

        // To be removed
        Shop shop = new Shop();
        shop.setShopId(1L);
        request.getSession().setAttribute("currentShop", shop);

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;

        if (currentShop != null && currentShop.getShopId() > 0) {
            list = iProductCategoryService.getProductCategoryList(currentShop.getShopId());
            // modelMap.put("list", list);
            // modelMap.put("success", true);
            return new Result<>(true, list);
        } else {
            // modelMap.put("success", false);
            // modelMap.put("errorMsg", "出错了");
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<>(false, ps.getState(), ps.getStateInfo());
        }
        // return modelMap;
    }


    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                   HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        for(ProductCategory pc:productCategoryList){
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try{
                ProductCategoryExecution pce = iProductCategoryService.batchAddProductCategory(productCategoryList);
                if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pce.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(HttpServletRequest request, Long productCategoryId) {
        Map<String, Object> modelMap = new HashMap<>();

        if (productCategoryId != null && productCategoryId > 0) {
            try {
                //获取目前的店铺信息
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                //开始删除 存在状态里
                ProductCategoryExecution pce = iProductCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pce.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg","请选择商品类别");
        }
        return modelMap;
    }
}
