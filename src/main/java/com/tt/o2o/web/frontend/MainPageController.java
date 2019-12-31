package com.tt.o2o.web.frontend;

import com.tt.o2o.entity.HeadLine;
import com.tt.o2o.entity.ShopCategory;
import com.tt.o2o.service.IHeadLineService;
import com.tt.o2o.service.IShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private IShopCategoryService iShopCategoryService;

    @Autowired
    private IHeadLineService iHeadLineService;

    /**
     * 初始化前端展示系统的主页信息，包括一级店铺类别列表以及头条列表
     */
    @RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        try{
            //获取一级店铺类别列表(即parentId为空的shopcategory)
            shopCategoryList = iShopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<>();
        try {
            //获取状态为1的列表
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = iHeadLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }
}
