package com.tt.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendAdminController {

    @RequestMapping(value = "index",method = RequestMethod.GET)
    private String index(){
        return "frontend/index";
    }


    @RequestMapping(value = "shoplist", method = RequestMethod.GET)
    private String shopList() {
        return "frontend/shoplist";
    }

    @RequestMapping(value = "shopdetail", method = RequestMethod.GET)
    private String shopDetail() {
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "productdetail", method = RequestMethod.GET)
    private String productDetail() {
        return "frontend/productdetail";
    }

}
