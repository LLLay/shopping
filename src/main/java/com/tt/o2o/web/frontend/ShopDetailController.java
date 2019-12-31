package com.tt.o2o.web.frontend;

import com.tt.o2o.dto.ProductExecution;
import com.tt.o2o.entity.Product;
import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.service.IProductCategoryService;
import com.tt.o2o.service.IProductService;
import com.tt.o2o.service.IShopService;
import com.tt.o2o.utlis.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private IShopService shopService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下的商品列别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<>();

        // 获取前台传过来的shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;

        if (shopId != -1) {
            // 获取店铺Id为shopId的店铺
            shop = shopService.getByShopId(shopId);
            // 获取店铺下面的商品类别列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取一页需要的显示条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 获取店铺Id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        // 空值判断
        if ((pageSize > -1) && (pageIndex > -1) && (shopId > -1)) {
            long productCategoryId = HttpServletRequestUtil.getLong(request,
                    "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request,
                    "productName");
            Product productCondition = compactProductCondition4Search(shopId,
                    productCategoryId, productName);
            ProductExecution pe = productService.getProductList(
                    productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition4Search(long shopId,
                                                   long productCategoryId, String productName) {
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
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}