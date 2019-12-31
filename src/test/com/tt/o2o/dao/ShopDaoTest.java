package com.tt.o2o.dao;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.Area;
import com.tt.o2o.entity.PersonInfo;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private IShopDao iShopDao;

    @Test
    public void testQueryShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> list =iShopDao.queryShopList(shopCondition, 0, 5);
        System.out.println(list.size());
    }

    @Test
    public void testQueryShopCount() {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        int count =iShopDao.queryShopCount(shopCondition);
        System.out.println(count);
    }
    @Test
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = iShopDao.queryByShopId(shopId);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getArea().getAreaId());
    }

    @Test
    public void testInsertShop(){
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
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setOwnerId(1L);

        int effectedNum = iShopDao.insertShop(shop);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试");
        shop.setShopAddr("测试");
        int effectedNum = iShopDao.updateShop(shop);
    }

    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = iShopDao.queryShopList(shopCondition,0,5);
        int count = iShopDao.queryShopCount(shopCondition);
    }
}
