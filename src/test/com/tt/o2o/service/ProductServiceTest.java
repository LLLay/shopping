package com.tt.o2o.service;

import com.tt.o2o.BaseTest;
import com.tt.o2o.dto.ImageHolder;
import com.tt.o2o.dto.ProductExecution;
import com.tt.o2o.entity.Product;
import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {

    @Autowired
    private IProductService iProductService;
    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试");
        product.setPriority(10);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        // 创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\Administrator\\Desktop\\123.png");
        InputStream thumbnail = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(), thumbnail);

        // 创建详情图片列表
        File thumbnailFile2 = new File("C:\\Users\\Administrator\\Desktop\\321.jpg");
        InputStream thumbnail2 = new FileInputStream(thumbnailFile2);
        ImageHolder imageHolder2 = new ImageHolder(thumbnailFile2.getName(), thumbnail2);

        List<ImageHolder> list = new ArrayList<>();
        // 不能使用同一个流, 报错！！！找了好久
        //list.add(imageHolder);
        list.add(imageHolder2);

        // 添加商品并验证
        ProductExecution productExecution = iProductService.addProduct(product, imageHolder, list);
    }

    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);

        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("111");
        product.setProductDesc("正式");
        product.setPriority(10);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        // 创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\Administrator\\Desktop\\321.jpg");
        InputStream thumbnail = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(), thumbnail);

        // 创建详情图片列表
        File thumbnailFile2 = new File("C:\\Users\\Administrator\\Desktop\\123.png");
        InputStream thumbnail2 = new FileInputStream(thumbnailFile2);
        ImageHolder imageHolder2 = new ImageHolder(thumbnailFile2.getName(), thumbnail2);

        List<ImageHolder> list = new ArrayList<>();
        // 不能使用同一个流, 报错！！！找了好久
        //list.add(imageHolder);
        list.add(imageHolder2);

        // 添加商品并验证
        ProductExecution productExecution = iProductService.modifyProduct(product, imageHolder, list);
    }

}
