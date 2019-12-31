package com.tt.o2o.service;

import com.tt.o2o.BaseTest;
import com.tt.o2o.dto.ShopExecution;
import com.tt.o2o.entity.Area;
import com.tt.o2o.entity.PersonInfo;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.entity.ShopCategory;
import com.tt.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private IShopService iShopService;

    @Test
    public void testQueryShopListAndCount(){

        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = iShopService.getShopList(shopCondition,1,2);
        System.out.println("店铺列表数"+se.getShopList().size());
        System.out.println("店铺总数"+se.getCount());
    }

    @Test
    public void testModifyShop() throws RuntimeException, FileNotFoundException {
        Shop shop  = new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改后的店铺名称");
        File shopImg = new File("D:\\123.png");
        InputStream inputStream = new FileInputStream(shopImg);
        ShopExecution shopExecution = iShopService.modifyShop(shop,inputStream,"123.png");
        System.out.println(shopExecution.getShop().getShopImg());
    }

    @Test
    public void testAddShop(){

        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        shop.setOwnerId(1L);


        //CommonsMultipartFile shopImg = new CommonsMultipartFile("C:\\Users\\Administrator\\Desktop\\123.png");
        //ShopExecution se = iShopService.addShop(shop,shopImg);

    }
}
