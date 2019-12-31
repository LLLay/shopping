package com.tt.o2o.web.frontend;

import com.tt.o2o.entity.Product;
import com.tt.o2o.service.IProductService;
import com.tt.o2o.utlis.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "listproductdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取前台传过来的ProductId
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        Product product = null;

        if(productId != -1){
            product = iProductService.getProductById(productId);
            modelMap.put("product",product);
            modelMap.put("success",true);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }
}
