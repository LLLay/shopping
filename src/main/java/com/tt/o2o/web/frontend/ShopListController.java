package com.tt.o2o.web.frontend;

import com.tt.o2o.dto.ShopExecution;
import com.tt.o2o.entity.Area;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.entity.ShopCategory;
import com.tt.o2o.service.IAreaService;
import com.tt.o2o.service.IShopCategoryService;
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
@RequestMapping("frontend")
public class ShopListController {

    @Autowired
    private IShopService iShopService;
    @Autowired
    private IShopCategoryService iShopCategoryService;
    @Autowired
    private IAreaService iAreaService;

    /**
     * 返回商品列表里ShopCategory列表（二级或者一级），以及区域信息列表
     */

    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> ListShopsPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //从前端请求获取parentId
        long parentId = HttpServletRequestUtil.getLong(request,"parentId");
        List<ShopCategory> shopCategoryList = null;
        if(parentId != -1){
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setParentId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList =iShopCategoryService.getShopCategoryList(shopCategoryCondition);

                modelMap.put("shopCategoryList",shopCategoryList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

        }else{
            try {
                shopCategoryList = iShopCategoryService.getShopCategoryList(null);

                modelMap.put("shopCategoryList",shopCategoryList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }
        List<Area> areaList = null;
        try {
            areaList =iAreaService.getAreaList();
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request,
                    "shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request,
                    "shopName");
            Shop shopCondition = compactShopCondition4Search(parentId,
                    shopCategoryId, areaId, shopName);
            ShopExecution se = iShopService.getShopList(shopCondition,
                    pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }

        return modelMap;
    }

    private Shop compactShopCondition4Search(long parentId,
                                             long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            // TODO
            // shopCondition.setParentCategory(parentCategory);
        }
        if (shopCategoryId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
