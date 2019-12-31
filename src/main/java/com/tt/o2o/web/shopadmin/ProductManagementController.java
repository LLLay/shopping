package com.tt.o2o.web.shopadmin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tt.o2o.dto.ImageHolder;
import com.tt.o2o.dto.ProductExecution;
import com.tt.o2o.entity.Product;
import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.enums.ProductStateEnum;
import com.tt.o2o.service.IProductCategoryService;
import com.tt.o2o.service.IProductService;
import com.tt.o2o.utlis.CodeUtil;
import com.tt.o2o.utlis.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IProductCategoryService iProductCategoryService;
    //支持上传商品详情图的最大数
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();

        //识别验证码
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        //接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String ProductStr = HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            // 如果请求中存在文件流，则取出相关文件(包括缩略图，和详情图)
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                // 取出缩略图并构建ImageHolder对象
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                // 取出详情图片列表并构建List<ImageHolder>列表，最多支持六张图片
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile =
                            (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);

                    if (productImgFile != null) {
                        // 如果取出第i个详情图片的文件流不为空 则将其加入列表
                        ImageHolder productImg = new ImageHolder(
                                productImgFile.getOriginalFilename(), productImgFile.getInputStream());

                        productImgList.add(productImg);
                    } else {
                        // 若取出的第i个详情图片文件流为空，则终止循环
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }


        try {
            // 尝试获取前端传过来的表单string流并将其转化成Product实体类
            product = mapper.readValue(ProductStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }


        // 如果Product信息，缩略图已经详情图片列表都不为空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                // 从session中获取当前的店铺id并赋值给product 减少对前端的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                // 执行并添加操作
                ProductExecution pe = iProductService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
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

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam long productId){
        Map<String,Object> modelMap = new HashMap<>();
        //非空判断
        if(productId > -1){
            Product product = iProductService.getProductById(productId);
            List<ProductCategory> productCategoryList = iProductCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty productId");
        }
        return modelMap;
    }


    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //是商品编辑时调用还是上下架操作的时候调用
        //若为前者则进行验证码判断，后者则跳过验证码
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //验证码
        if(!statusChange && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //前端接收参数的变量初始化
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //如存在文件流，则取出
        try {
            // 如果请求中存在文件流，则取出相关文件(包括缩略图，和详情图)
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                // 取出缩略图并构建ImageHolder对象
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                if(thumbnailFile != null){
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                }
                // 取出详情图片列表并构建List<ImageHolder>列表，最多支持六张图片
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile =
                            (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);

                    if (productImgFile != null) {
                        // 如果取出第i个详情图片的文件流不为空 则将其加入列表
                        ImageHolder productImg = new ImageHolder(
                                productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        try {
            String ProductStr = HttpServletRequestUtil.getString(request,"productStr");
            // 尝试获取前端传过来的表单string流并将其转化成Product实体类
            product = mapper.readValue(ProductStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }


        // 如果Product信息，缩略图已经详情图片列表都不为空，则开始进行商品添加操作
        if (product != null ) {
            try {
                // 从session中获取当前的店铺id并赋值给product 减少对前端的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);

                // 执行并更新操作
                ProductExecution pe = iProductService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
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

    private Product compactProductCondition(long shopId, long productCategoryId, String productName){
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


    @RequestMapping(value = "getproductlistbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductListByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取前台穿过来的每页要求返回的商品数
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        //从当前的session中获取店铺信息，主要获取shopId
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        //空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)) {
            // 获取传入的需要的检索条件
            long productCategoryId = HttpServletRequestUtil.getLong(request, "categoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            // 传入查询条件已经分页查询 返回相应的商品列表已经总数
            ProductExecution productExecution = iProductService.getProductList(productCondition, pageIndex, pageSize);



            modelMap.put("productList", productExecution.getProductList());
            modelMap.put("count", productExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or currentShop!!!");
        }
        return modelMap;
    }


}
